package com.warm.router.processor.base;

import com.warm.router.annotations.model.RouteInfo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * 作者：warm
 * 时间：2019-07-20 15:20
 * 描述：
 */
public abstract class BaseProcessor extends AbstractProcessor {
    public static final String ACTIVITY = "android.app.Activity";
    public static final String FRAGMENT = "android.support.v4.app.Fragment";


    public static Map<String, Integer> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put(ACTIVITY, RouteInfo.TYPE_ACTIVITY);
        TYPE_MAP.put(FRAGMENT, RouteInfo.TYPE_FRAGMENT);
    }


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

    protected boolean isActivity(TypeElement typeElement) {
        return mTypes.isSubtype(typeElement.asType(), mElementUtils.getTypeElement(ACTIVITY).asType());
    }

    protected boolean isFragment(TypeElement typeElement) {
        return mTypes.isSubtype(typeElement.asType(), mElementUtils.getTypeElement(FRAGMENT).asType());
    }
}
