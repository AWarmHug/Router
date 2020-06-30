package com.bingo.router.internal.chain;

import com.bingo.router.Interceptor;
import com.bingo.router.Request;

public class FragmentInterceptor implements Interceptor {

    public FragmentInterceptor() {
    }

    @Override
    public void intercept(Chain chain) {
        Request request = chain.request();
        if (request.getUri() == null) {
            return;
        }
    }

}
