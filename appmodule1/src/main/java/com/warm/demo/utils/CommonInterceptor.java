package com.warm.demo.utils;

import android.widget.Toast;

import com.warm.router.Interceptor;
import com.warm.router.Request;
import com.warm.router.annotations.RouteInterceptor;

@RouteInterceptor(name = "CommonInterceptor", isGlobal = true)
public class CommonInterceptor implements Interceptor {
    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();
        Toast.makeText(chain.getContext(), "这是CommonInterceptor", Toast.LENGTH_SHORT).show();
        chain.proceed(request);
    }
}
