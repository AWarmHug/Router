package com.bingo.router.processor.base;

import com.bingo.router.annotations.model.Const;
import com.bingo.router.annotations.model.RouteInfo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
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
    protected Map<String, String> mOptions;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mTypes = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
        mOptions = processingEnvironment.getOptions();
    }

    protected boolean isActivity(Element typeElement) {
        return mTypes.isSubtype(typeElement.asType(), mElementUtils.getTypeElement(ACTIVITY).asType());
    }

    protected boolean isFragment(Element typeElement) {
        return mTypes.isSubtype(typeElement.asType(), mElementUtils.getTypeElement(FRAGMENT).asType());
    }

    protected String getModuleName() {
        return mOptions.get(Const.KEY_MODULE_NAME);
    }
}
