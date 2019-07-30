package com.warm.router.demo;

/**
 * 作者：warm
 * 时间：2019-07-29 20:54
 * 描述：
 */
public interface Chain {

    //操作请求
    Action action();

    //执行
    void proceed(Action action);

    //终止
    void abort();

}
