package com.warm.router.annotations.model;

/**
 * 作者：warm
 * 时间：2019-07-20 15:48
 * 描述：
 */
public class RouteInfo {
    public static final int TYPE_ACTIVITY=1;
    public static final int TYPE_FRAGMENT=2;


    protected int type;
    protected String path;
    protected Class<?> target;

    private Class<?>[] interceptors;

    public RouteInfo(int type, String path, Class<?> target) {
        this.type = type;
        this.path = path;
        this.target = target;
    }

    public Class<?>[] getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(Class<?>[] interceptors) {
        this.interceptors = interceptors;
    }

    public Class<?> getTarget() {
        return target;
    }
}
