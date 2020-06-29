package com.bingo.demo.utils;

import android.widget.Toast;

import com.bingo.router.annotations.RouteInterceptor;
import com.bingo.router.Interceptor;
import com.bingo.router.Request;

@RouteInterceptor(name = "CommonInterceptor", isGlobal = true)
public class CommonInterceptor implements Interceptor {
    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();
        Toast.makeText(chain.getContext(), request.getUri().toString(), Toast.LENGTH_SHORT).show();
        chain.proceed(request);
    }
}
