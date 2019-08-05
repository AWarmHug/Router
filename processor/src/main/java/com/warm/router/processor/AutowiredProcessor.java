package com.warm.router.processor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.warm.router.annotations.Autowired;
import com.warm.router.annotations.model.AutowiredBinder;
import com.warm.router.annotations.model.Const;
import com.warm.router.processor.base.BaseProcessor;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * 作者：warm
 * 时间：2019-07-20 15:07
 * 描述：
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.warm.router.annotations.Autowired"})
public class AutowiredProcessor extends BaseProcessor {

    private Map<Element, Set<VariableElement>> mMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> autowireds = roundEnvironment.getElementsAnnotatedWith(Autowired.class);
        for (Element element : autowireds) {
            if (element instanceof VariableElement) {
                VariableElement vElement = (VariableElement) element;
                //这是获取owner 可能是fragment也可能是activity，或者其他
                Element tElement = vElement.getEnclosingElement();
                if (mMap.containsKey(tElement)) {
                    mMap.get(tElement).add(vElement);
                } else {
                    Set<VariableElement> vElementSet = new HashSet<>();
                    vElementSet.add(vElement);
                    mMap.put(tElement, vElementSet);
                }
            }
        }


        final MethodSpec.Builder loadBuilder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, AutowiredBinder.class), "binders");


        mMap.forEach(new BiConsumer<Element, Set<VariableElement>>() {
            @Override
            public void accept(Element element, Set<VariableElement> variableElements) {
                MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_BIND)
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .returns(void.class)
                        .addParameter(Object.class, "obj");
                builder.addStatement("if(!(obj instanceof $T)) return", TypeName.get(element.asType()));
                builder.addStatement("$T t=($T)obj", TypeName.get(element.asType()), TypeName.get(element.asType()));

                boolean isFragment = isFragment(element);
                if (isFragment) {
                    builder.addStatement("if(t.getArguments()==null) return");
                }

                for (VariableElement vElement : variableElements) {
                    builder.addCode(getBundleCode("t", element, vElement));
                }


                TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + Const.BINDER_CLASS_NAME)
                        .addSuperinterface(AutowiredBinder.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(builder.build())
                        .build();

                PackageElement pElement = (PackageElement) element.getEnclosingElement();
                JavaFile javaFile = JavaFile.builder(pElement.getQualifiedName().toString(), typeSpec).build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadBuilder.addStatement("binders.put($T.class.getName(),new $T())", ClassName.get(element.asType()), ClassName.get(pElement.getQualifiedName().toString(), element.getSimpleName() + Const.BINDER_CLASS_NAME));
            }
        });

        TypeSpec typeSpec = TypeSpec.classBuilder(Const.BINDER_LOADER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(loadBuilder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(Const.LOADER_PKG, typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public CodeBlock getBundleCode(String name, Element element, VariableElement vElement) {
        boolean isIntent = isActivity(element);

        Autowired autowired = vElement.getAnnotation(Autowired.class);
        String eName = "\"" + (!autowired.name().isEmpty() ? autowired.name() : vElement.getSimpleName().toString()) + "\"";

        if (vElement.asType().getKind().isPrimitive()) {
            String kindName = vElement.asType().toString().substring(0, 1).toUpperCase() + vElement.asType().toString().substring(1);
            return getDefaultTemplate(name, vElement, isIntent, eName, kindName);
        } else if (vElement.asType().getKind() == TypeKind.ARRAY) {
            TypeMirror typeMirror = ((ArrayType) vElement.asType()).getComponentType();
            if (typeMirror.getKind().isPrimitive()) {
                String kindName = typeMirror.toString().substring(0, 1).toUpperCase() + typeMirror.toString().substring(1) + "Array";
                return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
            } else {
                String kindName = ClassName.get(mElementUtils.getTypeElement(typeMirror.toString())).simpleName() + "Array";
                if (mTypes.isSameType(typeMirror, mElementUtils.getTypeElement(String.class.getName()).asType())) {
                    return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
                }
                if (mTypes.isSameType(typeMirror, mElementUtils.getTypeElement(CharSequence.class.getName()).asType())) {
                    return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
                }
                if (mTypes.isSubtype(typeMirror, mElementUtils.getTypeElement(Parcelable.class.getName()).asType())) {
                    return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
                }
            }
        } else {
            if (mTypes.isSameType(vElement.asType(), mElementUtils.getTypeElement(String.class.getName()).asType())) {
                return getNoDefaultTemplate(name, vElement, isIntent, eName, "String");
            }
            if (mTypes.isSameType(vElement.asType(), mElementUtils.getTypeElement(CharSequence.class.getName()).asType())) {
                return getNoDefaultTemplate(name, vElement, isIntent, eName, "CharSequence");
            }
            if (vElement.asType().toString().contains(List.class.getName()) || vElement.asType().toString().contains(ArrayList.class.getName())) {
                Pattern pattern = Pattern.compile("<(.*?)>");
                Matcher m = pattern.matcher(vElement.asType().toString());
                if (m.find()) {
                    String sou = m.group(1);
                    switch (ClassName.get(mElementUtils.getTypeElement(sou)).simpleName()) {
                        case "Integer":
                        case "String":
                        case "CharSequence":
                        case "Parcelable":
                            return getNoDefaultTemplate(name, vElement, isIntent, eName, ClassName.get(mElementUtils.getTypeElement(sou)).simpleName() + "ArrayList");
                        default:
                            if (mTypes.isSubtype(mElementUtils.getTypeElement(sou).asType(), mElementUtils.getTypeElement(Serializable.class.getName()).asType())) {
                                return getSerializableTemplate(name, vElement, isIntent, eName, "Serializable");
                            }
                    }
                }
            }
            if (mTypes.isSubtype(vElement.asType(), mElementUtils.getTypeElement(Serializable.class.getName()).asType())) {
                return getSerializableTemplate(name, vElement, isIntent, eName, "Serializable");
            }
            if (mTypes.isSubtype(vElement.asType(), mElementUtils.getTypeElement(Parcelable.class.getName()).asType())) {
                return getSerializableTemplate(name, vElement, isIntent, eName, "Parcelable");
            }
        }
        return CodeBlock.builder().build();
    }

    private CodeBlock getNoDefaultTemplate(String name, VariableElement vElement, boolean isIntent, String eName, String kindName) {

        if (isIntent) {
            return CodeBlock.builder()
                    .beginControlFlow("if(" + name + ".getIntent().get" + kindName + "Extra(" + eName + ")!=null)")
                    .addStatement(name + "." + vElement.getSimpleName() + "=" + name + ".getIntent().get" + kindName + "Extra(" + eName + ")")
                    .endControlFlow()
                    .build();
        } else {
            return CodeBlock.builder()
                    .beginControlFlow("if(" + name + ".getArguments().get" + kindName + "(" + eName + ")!=null)")
                    .addStatement(name + "." + vElement.getSimpleName() + "=" + name + ".getArguments().get" + kindName + "(" + eName + ")")
                    .endControlFlow()
                    .build();
        }
    }

    private CodeBlock getDefaultTemplate(String name, VariableElement vElement, boolean isIntent, String eName, String kindName) {

        if (isIntent) {
            return CodeBlock.builder()
                    .addStatement(name + "." + vElement.getSimpleName() + "=" + name + ".getIntent().get" + kindName + "Extra(" + eName + "," + name + "." + vElement.getSimpleName() + ")")
                    .build();
        } else {
            return CodeBlock.builder()
                    .addStatement(name + "." + vElement.getSimpleName() + "=" + name + ".getArguments().get" + kindName + "(" + eName + "," + name + "." + vElement.getSimpleName() + ")")
                    .build();
        }
    }


    private CodeBlock getSerializableTemplate(String name, VariableElement vElement, boolean isIntent, String eName, String kindName) {
        if (isIntent) {
            return CodeBlock.builder()
                    .beginControlFlow("if(" + name + ".getIntent().get" + kindName + "Extra(" + eName + ")!=null)")
                    .addStatement(name + "." + vElement.getSimpleName() + "=($T)" + name + ".getIntent().get" + kindName + "Extra(" + eName + ")", TypeName.get(vElement.asType()))
                    .endControlFlow()
                    .build();

        } else {
            return CodeBlock.builder()
                    .beginControlFlow("if(" + name + ".getArguments().get" + kindName + "(" + eName + ")!=null)")
                    .addStatement(name + "." + vElement.getSimpleName() + "=($T)" + name + ".getArguments().get" + kindName + "(" + eName + ")", TypeName.get(vElement.asType()))
                    .endControlFlow()
                    .build();
        }
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
