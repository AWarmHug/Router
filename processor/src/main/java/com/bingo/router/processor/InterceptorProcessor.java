package com.bingo.router.processor;

import com.bingo.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.bingo.router.annotations.RouteInterceptor;
import com.bingo.router.Const;
import com.bingo.router.Loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.bingo.router.annotations.RouteInterceptor"})
@SupportedOptions({"moduleName"})
public class InterceptorProcessor extends BaseProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> interceptors = roundEnvironment.getElementsAnnotatedWith(RouteInterceptor.class);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(ClassName.get(Map.class), TypeName.get(String.class), TypeName.get(mElementUtils.getTypeElement("com.bingo.router.Interceptor").asType())), "interceptors");

        List<String> globalKeys = new ArrayList<>();
        for (Element element : interceptors) {
            if (element instanceof TypeElement) {
                TypeElement e = (TypeElement) element;
                mMessager.printMessage(Diagnostic.Kind.WARNING, e.getQualifiedName());

                String className = e.getQualifiedName().toString();
                RouteInterceptor routeInterceptor = e.getAnnotation(RouteInterceptor.class);

                if (routeInterceptor.isGlobal()) {
                    builder.addStatement("interceptors.put($S,new $T())", routeInterceptor.name(), ClassName.get(e));
                    globalKeys.add(routeInterceptor.name());
                }
            }
        }

        FieldSpec mGlobalInterceptorKeys = FieldSpec.builder(String[].class, "mGlobalInterceptorKeys", Modifier.PUBLIC).build();

        if (!globalKeys.isEmpty()) {
            builder.addStatement("mGlobalInterceptorKeys=new $T[$L]", String.class, globalKeys.size());

            for (int i = 0; i < globalKeys.size(); i++) {
                String globalKey = globalKeys.get(i);
                builder.addStatement("mGlobalInterceptorKeys[$L] = $S", i, globalKey);
            }
        }
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(Const.INTERCEPTOR_LOADER_CLASS_NAME)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Loader.class), TypeName.get(mElementUtils.getTypeElement("com.bingo.router.Interceptor").asType())))
                .addModifiers(Modifier.PUBLIC)
                .addField(mGlobalInterceptorKeys)
                .addMethod(builder.build());

        JavaFile javaFile = JavaFile.builder(Const.LOADER_PKG + Const.DOT + getModuleName(), typeSpecBuilder.build()).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }
}
