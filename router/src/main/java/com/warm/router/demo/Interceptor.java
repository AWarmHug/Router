package com.warm.router.demo;

/**
 * 作者：warm
 * 时间：2019-07-29 20:54
 * 描述：
 */
public interface Interceptor {

    Response intercept(Chain chain);

}
