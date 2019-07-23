package com.warm.router.annotations.model;

/**
 * 作者：warm
 * 时间：2019-07-20 15:48
 * 描述：
 */
public class RouteInfo {
    public static final int TYPE_ACTIVITY=1;


    private int type;
    private String path;
    private Class<?> target;

    public RouteInfo(int type, String path, Class<?> target) {
        this.type = type;
        this.path = path;
        this.target = target;
    }
}
