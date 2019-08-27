package com.warm.router.internal.chain;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.warm.router.Interceptor;
import com.warm.router.Request;
import com.warm.router.internal.matcher.Matcher;
import com.warm.router.internal.matcher.MatcherCenter;

public class IntentInterceptor implements Interceptor {

    public IntentInterceptor() {
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
            intent.putExtras(request.getExtra());
            intent.addFlags(request.getFlags());
            intent.setAction(request.getAction());
        }
        int requestCode = request.getRequestCode();

        if (intent != null) {
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
            //成功
        } else {
            //失败
        }
    }

}
