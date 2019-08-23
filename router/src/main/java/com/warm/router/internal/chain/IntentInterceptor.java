package com.warm.router.internal.chain;

import com.warm.router.Interceptor;
import com.warm.router.Request;

public class IntentInterceptor implements Interceptor {
   private Callback mCallback;

    public IntentInterceptor() {
    }

    public IntentInterceptor(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void intercept(Chain chain) {
        Request request = chain.request();
        if (request.getUri() == null) {
            return;
        }
        if (mCallback!=null) {
            mCallback.callback();
        }
    }

}
