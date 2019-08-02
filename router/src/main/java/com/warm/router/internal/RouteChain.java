package com.warm.router.internal.chain;

import com.warm.router.Interceptor;
import com.warm.router.Request;

import java.util.List;

public class RouteChain implements Interceptor.Chain {
    private Request mRequest;
    private List<Interceptor> mInterceptors;

    public RouteChain(Request request, List<Interceptor> interceptors) {
        mRequest = request;
        mInterceptors = interceptors;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public void proceed(Request request) {
        if (mInterceptors.isEmpty()){
            return;
        }
        Interceptor interceptor =   mInterceptors.remove(0);
        interceptor.intercept(this);
    }
}
