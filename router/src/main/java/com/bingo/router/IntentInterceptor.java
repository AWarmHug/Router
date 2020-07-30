package com.bingo.router;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bingo.activityresult.ActivityResult;
import com.bingo.router.matcher.Matcher;
import com.bingo.router.matcher.MatcherCenter;

final class IntentInterceptor implements Interceptor {

    private RouteCallback mRouteCallback;

    public IntentInterceptor(RouteCallback callback) {
        this.mRouteCallback = callback;
    }

    @Override
    public void intercept(Chain chain) {
        Request request = chain.request();
        if (request.getUri() == null) {
            return;
        }

        Intent intent = null;
        for (Matcher matcher : MatcherCenter.sMatcher) {
            if (matcher.match(chain.getContext(), request.getUri(), request)) {
                Object target = matcher.generate(chain.getContext(), request.getUri(), request);
                if (target instanceof Intent) {
                    intent = (Intent) target;
                }
                break;
            }
        }
        if (intent != null) {
            request.putParameter();
            intent.putExtras(request.getExtras());
            intent.addFlags(request.getFlags());
            intent.setAction(request.getAction());
        }
        int requestCode = request.getRequestCode();

        if (intent != null) {
            try {
                if (request.mOnResultCallback != null) {
                    ActivityResult result;
                    if (chain.getFragment() != null) {
                        result = new ActivityResult(chain.getFragment());
                    } else {
                        if (chain.getContext() instanceof FragmentActivity) {
                            result = new ActivityResult((FragmentActivity) chain.getContext());
                        } else {
                            throw new RuntimeException("context !instanceof FragmentActivity");
                        }
                    }
                    result.startActivityForResult(intent,request.mOnResultCallback);
                    if (mRouteCallback != null) {
                        mRouteCallback.onFail(request, new RuntimeException("context !instanceof FragmentActivity"));
                    }
                } else {
                    if (requestCode != -1) {
                        if (chain.getFragment() != null) {
                            chain.getFragment().startActivityForResult(intent, requestCode, request.getOptionsBundle());
                        } else {
                            ActivityCompat.startActivityForResult((Activity) chain.getContext(), intent, requestCode, request.getOptionsBundle());
                        }
                    } else {
                        if (chain.getFragment() != null) {
                            chain.getFragment().startActivityForResult(intent, requestCode, request.getOptionsBundle());
                        } else {
                            ActivityCompat.startActivity(chain.getContext(), intent, request.getOptionsBundle());
                        }
                    }
                    if (mRouteCallback != null) {
                        mRouteCallback.onSuccess(request);
                    }
                }
            } catch (Exception e) {
                if (mRouteCallback != null) {
                    mRouteCallback.onFail(request, e);
                }
            }
        } else {
            if (mRouteCallback != null) {
                mRouteCallback.onNoFound(request);
            }
        }
    }

}
