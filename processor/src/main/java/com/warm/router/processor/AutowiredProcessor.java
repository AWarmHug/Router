package com.warm.router.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.warm.router.annotations.Autowired;
import com.warm.router.annotations.model.AutowiredBinder;
import com.warm.router.annotations.model.Const;
import com.warm.router.annotations.model.RouteInfo;
import com.warm.router.processor.base.BaseProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

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
        mMap.forEach(new BiConsumer<Element, Set<VariableElement>>() {
            @Override
            public void accept(Element element, Set<VariableElement> variableElements) {
                MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_BIND)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(Object.class, "obj");
                builder.addStatement("if(!(obj instanceof $T)) return",TypeName.get(element.asType()));
                builder.addStatement("$T t=($T)obj",TypeName.get(element.asType()),TypeName.get(element.asType()));

                boolean isFragment = isFragment(element);
                if (isFragment) {
                    builder.addStatement("if(t.getArguments()==null) return");
                }

                for (VariableElement vElement : variableElements) {
                    builder.addStatement("t." + vElement.getSimpleName() + "=" + getBundleName("t", element, vElement));
                }


                TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + Const.BINDER_LOADER_CLASS_NAME)
                        .addSuperinterface(AutowiredBinder.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(builder.build())
                        .build();

               PackageElement pElement= (PackageElement) element.getEnclosingElement();
                JavaFile javaFile = JavaFile.builder(pElement.getQualifiedName().toString(), typeSpec).build();
                try {
                    javaFile.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    public String getBundleName(String name, Element element, VariableElement vElement) {
        boolean isIntent = isActivity(element);

        Autowired autowired = vElement.getAnnotation(Autowired.class);
        String eName = "\"" + (!autowired.name().isEmpty() ? autowired.name() : vElement.getSimpleName().toString()) + "\"";

        switch (vElement.asType().getKind()) {
            case INT:
                if (isIntent) {
                    return name + ".getIntent().getIntExtra(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                } else {
                    return name + ".getArguments().getInt(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                }
            case BYTE:
                if (isIntent) {
                    return name + ".getIntent().getByteExtra(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                } else {
                    return name + ".getArguments().getByte(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                }
            case CHAR:
                break;
            case LONG:
                if (isIntent) {
                    return name + ".getIntent().getLongExtra(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                } else {
                    return name + ".getArguments().getLong(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                }
            default:
                if (mTypes.isSameType(vElement.asType(), mElementUtils.getTypeElement(String.class.getName()).asType())) {
                    if (isIntent) {
                        return name + ".getIntent().getStringExtra(" + eName + ")!=null?" + name + ".getIntent().getStringExtra(" + eName + "):" + name + "." + vElement.getSimpleName();
                    } else {
                        return name + ".getArguments().getString(" + eName + "," + name + "." + vElement.getSimpleName() + ")";
                    }
                }
                break;
        }


        return "";
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
