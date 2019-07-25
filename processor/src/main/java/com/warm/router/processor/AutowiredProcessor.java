package com.warm.router.processor;

import com.google.auto.service.AutoService;
import com.warm.router.processor.base.BaseProcessor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

/**
 * 作者：warm
 * 时间：2019-07-20 15:07
 * 描述：
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.warm.router.annotations.Autowired"})
public class AutowiredProcessor extends BaseProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
