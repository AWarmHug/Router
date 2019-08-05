package com.warm.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.warm.router.annotations.model.RouteInfo;
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
        //针对Intent进行匹配
        Context context = (Context) obj;

//        List<Interceptor> interceptors = new ArrayList<>();
//        interceptors.add(new IntentInterceptor());
//
//        RouteChain chain = new RouteChain(mRequest, interceptors);
//        chain.proceed(mRequest);

        Intent intent = null;
        for (Matcher matcher : MatcherCenter.sMatcher) {
            if (matcher.match(context, mRequest.getUri(), mRequest)) {
                Object target = matcher.generate(context, mRequest.getUri(), getClazz());
                if (target instanceof Intent) {
                    intent = (Intent) target;
                }
                break;
            }
        }
        if (intent != null) {
            intent.putExtras(mRequest.getExtra());
        }
        return intent;
    }

    @Override
    public Fragment getFragment(Object obj) {
        //针对Fragment进行匹配
        Context context = (Context) obj;
        Fragment fragment = null;
        for (Matcher matcher : MatcherCenter.sMatcher) {
            if (matcher.match(context, mRequest.getUri(), mRequest)) {
                Object target = matcher.generate(context, mRequest.getUri(), getClazz());
                if (target instanceof Fragment) {
                    fragment = (Fragment) target;
                }
                break;
            }
        }
        if (fragment != null) {
            fragment.setArguments(mRequest.getExtra());
        }
        return fragment;
    }

    @Override
    public void start(Context context) {
        Intent intent = getIntent(context);

        int requestCode = mRequest.getRequestCode();

        if (intent != null) {
            if (requestCode != -1) {
                ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, mRequest.getOptionsBundle());
            } else {
                ActivityCompat.startActivity(context, intent, mRequest.getOptionsBundle());
            }
        }
    }

    @Override
    public void start(Fragment fragment) {
        Intent intent = getIntent(fragment.getActivity());
        int requestCode = mRequest.getRequestCode();
        if (intent != null) {
            if (requestCode != -1) {
                fragment.startActivityForResult(intent, requestCode);
            } else {
                fragment.startActivity(intent, mRequest.getOptionsBundle());
            }
        }
    }

    @Nullable
    public Class<?> getClazz() {
        RouteInfo info = Router.mRouteInfoMap.get(mRequest.getUri().getPath());
        if (info != null) {
            return info.getTarget();
        }
        return null;
    }
}
