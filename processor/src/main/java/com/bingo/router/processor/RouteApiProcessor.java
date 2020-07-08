package com.bingo.router.processor;

import android.os.Parcelable;

import com.bingo.router.Const;
import com.bingo.router.Loader;
import com.bingo.router.Utils;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.PathClass;
import com.bingo.router.annotations.Route;
import com.bingo.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.bingo.router.annotations.Route"})
@SupportedOptions({"moduleName"})
public class RouteApiProcessor extends BaseProcessor {
    private TypeMirror TYPE_REQUEST;
    private TypeMirror TYPE_IROUTE;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        TYPE_REQUEST = mElementUtils.getTypeElement("com.bingo.router.Request").asType();
        TYPE_IROUTE = mElementUtils.getTypeElement("com.bingo.router.IRoute").asType();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routeApis = roundEnvironment.getElementsAnnotatedWith(Route.class);
        List<ExecutableElement> elements = new ArrayList<>();
        for (Element element : routeApis) {
            if (element instanceof ExecutableElement && element.getKind() == ElementKind.METHOD) {
                System.out.println(element);
                elements.add((ExecutableElement) element);
            }
        }

        FieldSpec startBy = FieldSpec.builder(Object.class, "mStartBy", Modifier.PRIVATE).build();

        MethodSpec.Builder constructorContext = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClassName.bestGuess(CONTEXT), "startBy").build());

        constructorContext.addStatement("this.mStartBy=startBy");

        MethodSpec.Builder constructorFragment = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClassName.bestGuess(FRAGMENT), "startBy").build());

        constructorFragment.addStatement("this.mStartBy=startBy");

        Map<TypeElement, List<MethodSpec>> map = new HashMap<>();

        for (ExecutableElement element : elements) {
            List<ParameterSpec> parameterSpecs = new ArrayList<>();
            for (VariableElement variableElement : element.getParameters()) {
                parameterSpecs.add(ParameterSpec.get(variableElement));
            }

            MethodSpec.Builder builder = MethodSpec.methodBuilder(element.getSimpleName().toString())
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.get(element.getReturnType()))
                    .addParameters(parameterSpecs);

            Route route = element.getAnnotation(Route.class);

            String path = route.value();
            if (path.isEmpty()) {
                try {
                    //查看相关内容
                    //https://www.cnblogs.com/fuckingaway/p/6703021.html
                    //https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
                    route.pathClass();
                } catch (MirroredTypeException mte) {
                    TypeMirror value = mte.getTypeMirror();
                    String valueClass = value.toString();
                    TypeElement typeElement = mElementUtils.getTypeElement(valueClass);
                    path = Utils.pathByPathClass(typeElement.getAnnotation(PathClass.class));
                }
            }

            TypeMirror returnType = element.getReturnType();
            if (returnType.getKind() == TypeKind.VOID) {
                builder.addCode("$T.newRequest($S)", TypeName.get(mElementUtils.getTypeElement("com.bingo.router.Router").asType()), path);
            } else {
                builder.addCode("return $T.newRequest($S)", TypeName.get(mElementUtils.getTypeElement("com.bingo.router.Router").asType()), path);
            }


            List<? extends VariableElement> parameters = element.getParameters();

            for (int i = 0; i < parameters.size(); i++) {
                VariableElement variableElement = parameters.get(i);


                Parameter parameter = variableElement.getAnnotation(Parameter.class);
                if (parameter != null) {
                    if (variableElement.asType().getKind().isPrimitive()) {
                        putPrimitiveParameter(builder, variableElement);
                    } else if (variableElement.asType().getKind() == TypeKind.ARRAY) {
                        TypeMirror typeMirror = ((ArrayType) variableElement.asType()).getComponentType();
//                        if (typeMirror.getKind().isPrimitive()) {
//                            String kindName = typeMirror.toString().substring(0, 1).toUpperCase() + typeMirror.toString().substring(1) + "Array";
//                            return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
//                        } else {
//                            String kindName = ClassName.get(mElementUtils.getTypeElement(typeMirror.toString())).simpleName() + "Array";
//                            if (mTypes.isSameType(typeMirror, mElementUtils.getTypeElement(String.class.getName()).asType())) {
//                                return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
//                            }
//                            if (mTypes.isSameType(typeMirror, mElementUtils.getTypeElement(CharSequence.class.getName()).asType())) {
//                                return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
//                            }
//                            if (mTypes.isSubtype(typeMirror, mElementUtils.getTypeElement(Parcelable.class.getName()).asType())) {
//                                return getNoDefaultTemplate(name, vElement, isIntent, eName, kindName);
//                            }
//                        }
                    } else {
                        if (mTypes.isSameType(variableElement.asType(), mElementUtils.getTypeElement(String.class.getName()).asType())) {
                            putParameter("putString", builder, variableElement);
                        } else if (mTypes.isSameType(variableElement.asType(), mElementUtils.getTypeElement(CharSequence.class.getName()).asType())) {
                            putParameter("putCharSequence", builder, variableElement);
                        } else if (mTypes.isSubtype(variableElement.asType(), mElementUtils.getTypeElement(Serializable.class.getName()).asType())) {
                            putParameter("putSerializable", builder, variableElement);
                        } else if (mTypes.isSubtype(variableElement.asType(), mElementUtils.getTypeElement(Parcelable.class.getName()).asType())) {
                            putParameter("putParcelable", builder, variableElement);
                        }
                    }
                }
            }

            if (mTypes.isSameType(returnType, TYPE_REQUEST)) {
                builder.addCode(";\n");
            } else if (mTypes.isSameType(returnType, TYPE_IROUTE)) {
                builder.addCode("\n.build();\n");
            } else if (returnType.getKind() == TypeKind.VOID) {
                builder.addCode("\n.startBy(this.mStartBy);\n");
            }


            TypeElement tElement = (TypeElement) element.getEnclosingElement();
            List<MethodSpec> methodSpecs = map.get(tElement);
            if (methodSpecs == null) {
                methodSpecs = new ArrayList<>();
                map.put(tElement, methodSpecs);
            }
            methodSpecs.add(builder.build());
        }
        Map<TypeElement, String> clazzMap = new HashMap<>();

        map.forEach(new BiConsumer<TypeElement, List<MethodSpec>>() {
            @Override
            public void accept(TypeElement typeElement, List<MethodSpec> methodSpecs) {

                String pkgName = mElementUtils.getPackageOf(typeElement).toString();
                String typeName = typeElement.getQualifiedName().toString().replace(pkgName + ".", "").replace(".", "$") + "Impl";

                clazzMap.put(typeElement, pkgName + "." + typeName);

                TypeSpec typeSpec = TypeSpec.classBuilder(typeName)
                        .addSuperinterface(TypeName.get(typeElement.asType()))
                        .addModifiers(Modifier.PUBLIC)
                        .addField(startBy)
                        .addMethod(constructorContext.build())
                        .addMethod(constructorFragment.build())
                        .addMethods(methodSpecs)
                        .build();
                JavaFile javaFile = JavaFile.builder(pkgName, typeSpec).build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if (!clazzMap.isEmpty()) {
            createRoutePathClass(clazzMap);
        }

        return false;
    }

    private void createRoutePathClass(Map<TypeElement, String> clazzMap) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(Map.class, Class.class, Class.class), "routePath");

        clazzMap.forEach(new BiConsumer<TypeElement, String>() {
            @Override
            public void accept(TypeElement typeElement, String s2) {
                builder.addStatement("routePath.put($N.class,$N.class)", typeElement.getQualifiedName(), s2);
            }
        });

        TypeSpec typeSpec = TypeSpec.classBuilder(Const.ROUTER_PATH_LOADER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(Loader.class, Class.class, Class.class))
                .addMethod(builder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(Const.LOADER_PKG + Const.DOT + getModuleName(), typeSpec).build();

        try {

            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void putPrimitiveParameter(MethodSpec.Builder builder, VariableElement variableElement) {
        String kindName = variableElement.asType().toString().substring(0, 1).toUpperCase() + variableElement.asType().toString().substring(1);
        putParameter("put" + kindName, builder, variableElement);
    }

    private void putParameter(String code, MethodSpec.Builder builder, VariableElement variableElement) {
        String name = getName(variableElement, variableElement.getAnnotation(Parameter.class));
        builder.addCode("\n." + code + "($S,$L)", name, variableElement.getSimpleName());
    }

    private String getName(VariableElement variableElement, Parameter parameter) {
        String name;
        if (!parameter.value().isEmpty()) {
            name = parameter.value();
        } else {
            name = variableElement.getSimpleName().toString();
        }
        return name;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
