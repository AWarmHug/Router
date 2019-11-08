package com.bingo.router.internal.chain;

import com.bingo.router.Interceptor;
import com.bingo.router.Request;

public class FragmentInterceptor implements Interceptor {

    private Callback mCallback;

    public FragmentInterceptor() {
    }

    public FragmentInterceptor(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void intercept(Chain chain) {
        Request request = chain.request();
        if (request.getUri() == null) {
            return;
        }
        if (mCallback != null) {
            mCallback.callback();
        }
    }

}
