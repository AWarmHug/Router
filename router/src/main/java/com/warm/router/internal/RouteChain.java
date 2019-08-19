package com.warm.router.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.warm.router.Interceptor;
import com.warm.router.Request;

import java.util.List;

public class RouteChain implements Interceptor.Chain {

    private Object mObject;
    private Request mRequest;
    private List<Interceptor> mInterceptors;

    public RouteChain(Object object, Request request, List<Interceptor> interceptors) {
        mObject = object;
        mRequest = request;
        mInterceptors = interceptors;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public void proceed(Request request) {
        if (mInterceptors.isEmpty()) {
            return;
        }
        Interceptor interceptor = mInterceptors.remove(0);
         interceptor.intercept(this);
    }

    @Nullable
    @Override
    public Context getContext() {
        if (mObject instanceof Context) {
            return (Context) mObject;
        }
        if (mObject instanceof Fragment) {
            return ((Fragment) mObject).getContext();
        }
        return null;
    }

    @Nullable
    @Override
    public Fragment getFragment() {
        if (mObject instanceof Fragment) {
            return (Fragment) mObject;
        }
        return null;
    }
}
