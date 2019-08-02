package com.warm.router.internal.chain;

import com.warm.router.Interceptor;
import com.warm.router.Request;
import com.warm.router.Router;

public class IntentInterceptor implements Interceptor {
    @Override
    public void intercept(Chain chain) {
        Request request = chain.request();
        if (request.getUri() == null) {
            return;
        }


        chain.proceed(chain.request());
    }
}
