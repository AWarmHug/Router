package com.warm.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.warm.router.internal.chain.IntentInterceptor;
import com.warm.router.internal.RouteChain;
import com.warm.router.internal.matcher.Matcher;
import com.warm.router.internal.matcher.MatcherCenter;

import java.util.ArrayList;
import java.util.List;

public class RouteClient implements IRoute {

    private Request mRequest;

    @Override
    public IRoute build(Uri uri) {
        return build(new Request(uri));
    }

    @Override
    public IRoute build(Request request) {
        mRequest = request;
        return this;
    }

    @Override
    public IRoute with(String key, Object value) {
        Bundle bundle = mRequest.getExtra();
        if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (long) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (int) value);
        }

        return this;
    }

    @Override
    public IRoute withRequestCode(int requestCode) {
        mRequest.setRequestCode(requestCode);
        return this;
    }

    @Override
    public Intent getIntent(Object obj) {
        List<Interceptor> interceptors=new ArrayList<>();
        interceptors.add(new IntentInterceptor());


        RouteChain chain=new RouteChain(mRequest,interceptors);
        chain.proceed(mRequest);

        for (Matcher matcher:MatcherCenter.sMatcher) {
//            if (matcher.match())
        }
        //针对Intent进行匹配
        return null;
    }

    @Override
    public Fragment getFragment(Object obj) {
        //针对Fragment进行匹配
        return null;
    }

    @Override
    public void start(Context context) {


    }

    @Override
    public void start(Fragment fragment) {

    }
}
