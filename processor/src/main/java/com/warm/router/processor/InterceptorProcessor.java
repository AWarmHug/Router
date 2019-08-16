package com.warm.router.processor;

import com.google.auto.service.AutoService;
import com.warm.router.processor.base.BaseProcessor;

import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.warm.router.annotations.RouteInterceptor"})
@SupportedOptions({"moduleName"})
public class InterceptorProcessor extends BaseProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
