package com.bingo.router;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bingo.router.annotations.model.RouteInfo;
import com.bingo.router.internal.RouteChain;
import com.bingo.router.internal.chain.FragmentInterceptor;
import com.bingo.router.internal.chain.IntentInterceptor;
import com.bingo.router.internal.matcher.Matcher;
import com.bingo.router.internal.matcher.MatcherCenter;

import java.util.ArrayList;
import java.util.List;

class RealRoute implements IRoute {
    private Request mRequest;

    public RealRoute(Request request) {
        mRequest = request;
    }

    @Override
    public Request newRequest(Uri uri) {
        return newRequest(new Request(uri));
    }

    @Override
    public Request newRequest(Request request) {
        return request;
    }

    @Nullable
    @Override
    public Fragment getFragment(Context context) {
        //针对Fragment进行匹配
        //添加全局拦截器
        List<Interceptor> interceptors = new ArrayList<>(Router.sGlobalInterceptors);
        //添加针对拦截器
        RouteInfo info = Router.mRouteInfoMap.get(mRequest.getUri().getPath());
        if (info != null && info.getInterceptorKeys() != null) {
            for (String key : info.getInterceptorKeys()) {
                interceptors.add(Router.mInterceptorMap.get(key));
            }
        }

        if (!mRequest.getInterceptors().isEmpty()) {
            interceptors.addAll(mRequest.getInterceptors());
        }

        interceptors.add(new FragmentInterceptor());

        RouteChain chain = new RouteChain(context, mRequest, interceptors);
        chain.proceed(mRequest);

        Fragment fragment = null;
        for (Matcher matcher : MatcherCenter.sMatcher) {
            if (matcher.match(context, mRequest.getUri(), mRequest)) {
                Object target = matcher.generate(context, mRequest.getUri(), mRequest);
                if (target instanceof Fragment) {
                    fragment = (Fragment) target;
                }
                break;
            }
        }
        if (fragment != null) {
            fragment.setArguments(mRequest.getExtras());
        }
        return fragment;
    }

    @Override
    public void start(final Object obj) {

        //添加全局拦截器
        List<Interceptor> interceptors = new ArrayList<>(Router.sGlobalInterceptors);
        //添加针对拦截器
        final RouteInfo info = Router.mRouteInfoMap.get(mRequest.getUri().getPath());
        if (info != null && info.getInterceptorKeys() != null) {
            for (String key : info.getInterceptorKeys()) {
                interceptors.add(Router.mInterceptorMap.get(key));
            }
        }

        if (!mRequest.getInterceptors().isEmpty()) {
            interceptors.addAll(mRequest.getInterceptors());
        }


        IntentInterceptor intentInterceptor = new IntentInterceptor();

        interceptors.add(intentInterceptor);

        RouteChain chain = new RouteChain(obj, mRequest, interceptors);
        chain.proceed(mRequest);

    }


}
