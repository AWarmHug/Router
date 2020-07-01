package com.bingo.router;

final class FragmentInterceptor implements Interceptor {

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
