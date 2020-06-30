package com.bingo.demo.utils.route;

import android.net.Uri;

import com.bingo.demo.App;
import com.bingo.router.Interceptor;
import com.bingo.router.Request;
import com.bingo.router.annotations.RouteInterceptor;

@RouteInterceptor(name = "ErrorInterceptor", isGlobal = true)
public class ErrorInterceptor implements Interceptor {
    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();
        String path = request.getUri().getPath();
        String newPath = App.getInstance().getErrorRoute().get(path);
        if (newPath != null) {
            request.setUri(Uri.parse(newPath));
        }
        chain.proceed(request);
    }
}
