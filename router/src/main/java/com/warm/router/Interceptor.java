package com.warm.router;

public interface Interceptor {

    void intercept(Chain chain);


    interface Chain {

        Request request();

        void proceed(Request request);


    }


}
