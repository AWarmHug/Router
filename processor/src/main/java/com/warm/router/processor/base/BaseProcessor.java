package com.warm.router.processor.base;

import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * 作者：warm
 * 时间：2019-07-20 15:20
 * 描述：
 */
public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager mMessager;
    protected Elements mElementUtils;
    protected Types mTypes;
    protected Filer mFiler;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mTypes = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
    }

}
