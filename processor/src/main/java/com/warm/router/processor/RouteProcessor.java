package com.warm.router.processor;

import com.google.auto.service.AutoService;
import com.warm.router.annotations.Route;
import com.warm.router.processor.base.BaseProcessor;

import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * 作者：warm
 * 时间：2019-07-20 15:07
 * 描述：
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.warm.router.annotations.Route"})
public class RouteProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> routes = roundEnvironment.getElementsAnnotatedWith(Route.class);
        for (Element element : routes) {
            if (element instanceof TypeElement){
                TypeElement e= (TypeElement) element;

            }

        }
        return true;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
