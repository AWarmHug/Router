package com.warm.router.processor.base;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;

/**
 * 作者：warm
 * 时间：2019-07-20 15:20
 * 描述：
 */
public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager mMessager;

    protected Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
    }

}
