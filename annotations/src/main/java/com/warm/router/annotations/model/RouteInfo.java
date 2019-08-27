package com.warm.router.annotations.model;

/**
 * 作者：warm
 * 时间：2019-07-20 15:48
 * 描述：
 */
public class RouteInfo {
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_FRAGMENT = 2;


    protected int type;
    protected String path;
    protected Class<?> target;
    private String[] interceptorKeys;

    public RouteInfo(int type, String path, Class<?> target) {
        this.type = type;
        this.path = path;
        this.target = target;
    }

    public String[] getInterceptorKeys() {
        return interceptorKeys;
    }

    public void setInterceptorKeys(String[] interceptorKeys) {
        this.interceptorKeys = interceptorKeys;
    }

    public Class<?> getTarget() {
        return target;
    }
}
