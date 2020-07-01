package com.bingo.router;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bingo.router.Interceptor;
import com.bingo.router.Request;

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

    @NonNull
    @Override
    public Context getContext() {
        Context context = null;
        if (mObject instanceof Context) {
            context = (Context) mObject;
        }
        if (mObject instanceof Fragment) {
            context = ((Fragment) mObject).requireContext();
        }
        assert context != null;
        return context;
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
