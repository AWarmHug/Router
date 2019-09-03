package com.warm.router.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.warm.router.annotations.Parameter;
import com.warm.router.annotations.Route;
import com.warm.router.processor.base.BaseProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import javax.lang.model.type.TypeMirror;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.warm.router.annotations.Route"})
@SupportedOptions({"moduleName"})
public class RouteApiProcessor extends BaseProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routeApis = roundEnvironment.getElementsAnnotatedWith(Route.class);
        Element typeE = null;
        List<ExecutableElement> elements = new ArrayList<>();
        for (Element element : routeApis) {
            if (element instanceof ExecutableElement && element.getKind() == ElementKind.METHOD) {
                System.out.println(element);
                typeE = element.getEnclosingElement();
                elements.add((ExecutableElement) element);
            }
        }
        List<MethodSpec> methodSpecs = new ArrayList<>();

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

            /**
             * Router.newRequest("test/detail")
             *                         .putInt("type", 1)
             *                         .build()
             *                         .start(MainActivity.this);
             */
            Route route = element.getAnnotation(Route.class);
            builder.addCode("$T.newRequest($S)\n", TypeName.get(mElementUtils.getTypeElement("com.warm.router.Router").asType()), route.value());

            for (VariableElement variableElement : element.getParameters()) {

                Parameter parameter = variableElement.getAnnotation(Parameter.class);
                String name;
                if (parameter != null && !parameter.name().isEmpty()) {
                    name = parameter.name();
                } else {
                    name = variableElement.getSimpleName().toString();
                }

                if (mTypes.isSameType(variableElement.asType(), mElementUtils.getTypeElement(String.class.getName()).asType())) {
                    builder.addCode(".putString($S,$L)\n", name, name);
                }else if (variableElement.asType().getKind().isPrimitive()){
                    switch (variableElement.asType().getKind()){
                        case INT:
                            builder.addCode(".putInt($S,$L)\n", name, name);
                            break;
                        case BYTE:
                            builder.addCode(".putByte($S,$L)\n", name, name);
                            break;
                        case CHAR:
                            builder.addCode(".putChar($S,$L)\n", name, name);
                            break;
                        case LONG:
                            builder.addCode(".putLong($S,$L)\n", name, name);
                            break;
                    }
                }

                //TODO
                builder.addStatement(".build()");
            }

            methodSpecs.add(builder.build());
        }

        if (typeE == null) {
            return false;
        }

        TypeSpec typeSpec = TypeSpec.classBuilder(typeE.getSimpleName().toString() + "Ipml")
                .addSuperinterface(TypeName.get(typeE.asType()))
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methodSpecs)
                .build();
        JavaFile javaFile = JavaFile.builder(typeE.getEnclosingElement().toString(), typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
