package com.warm.router.processor;


import android.app.Activity;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.warm.router.annotations.Route;
import com.warm.router.annotations.model.RouteInfo;
import com.warm.router.processor.base.BaseProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.warm.router.annotations.model.Const;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.warm.router.annotations.Route"})
public class RouteProcessor extends BaseProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routes = roundEnvironment.getElementsAnnotatedWith(Route.class);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(Const.METHOD_LODE)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, RouteInfo.class), "route", Modifier.FINAL);

        for (Element element : routes) {
            if (element instanceof TypeElement) {
                TypeElement e = (TypeElement) element;
                mMessager.printMessage(Diagnostic.Kind.WARNING, e.getQualifiedName());
                int type = 0;
                if (mTypes.isSubtype(e.asType(), mElementUtils.getTypeElement(Activity.class.getName()).asType())) {
                    type = RouteInfo.TYPE_ACTIVITY;
                } else {

                }
                String className = e.getQualifiedName().toString();
                Route route = e.getAnnotation(Route.class);
                String path = route.value();
//                RouteInfo info = new RouteInfo(type, path, Class.forName(className));

                builder.addStatement("route.put($S,new $T(" + type + ",$S,$T.class))", path, TypeName.get(RouteInfo.class), path, ClassName.get(e));

            }

        }
        builder.build();

        TypeSpec typeSpec = TypeSpec.classBuilder(Const.ROUTE_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(Const.ROUTE_PKG, typeSpec).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
