package com.warm.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拦截器分为两种，一种是全局拦截器，还有一种是针对某个类的拦截器
 * apt会自动将全局拦截器加入
 * 针对某个类的，会在跳转时加入
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouteInterceptor {
    String name();

    boolean isGlobal() default false;
}
