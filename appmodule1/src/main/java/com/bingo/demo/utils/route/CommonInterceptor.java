package com.bingo.demo.utils.route;

import android.util.Log;
import android.widget.Toast;

import com.bingo.router.Interceptor;
import com.bingo.router.Request;
import com.bingo.router.annotations.RouteInterceptor;

@RouteInterceptor(name = "CommonInterceptor", isGlobal = true)
public class CommonInterceptor implements Interceptor {
    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();
        Log.d("Router", "Uri = " + request.getUri().toString());
        Toast.makeText(chain.getContext(), request.getUri().toString(), Toast.LENGTH_SHORT).show();
        chain.proceed(request);
    }
}
