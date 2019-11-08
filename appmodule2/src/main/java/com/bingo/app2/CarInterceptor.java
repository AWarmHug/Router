package com.bingo.app2;

import android.widget.Toast;

import com.bingo.router.annotations.RouteInterceptor;
import com.bingo.router.Interceptor;
import com.bingo.router.Request;

@RouteInterceptor(name = "CarInterceptor")
public class CarInterceptor implements Interceptor {
    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();
        Toast.makeText(chain.getContext(), "这是CarInterceptor", Toast.LENGTH_SHORT).show();
        chain.proceed(request);
    }
}
