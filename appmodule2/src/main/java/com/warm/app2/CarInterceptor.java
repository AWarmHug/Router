package com.warm.app2;

import android.widget.Toast;

import com.warm.router.Interceptor;
import com.warm.router.Request;
import com.warm.router.annotations.RouteInterceptor;

@RouteInterceptor(name = "CarInterceptor")
public class CarInterceptor implements Interceptor {
    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();
        Toast.makeText(chain.getContext(), "这是CarInterceptor", Toast.LENGTH_SHORT).show();
        chain.proceed(request);
    }
}
