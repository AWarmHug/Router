package com.warm.router;

import android.net.Uri;

import com.warm.router.annotations.model.AutowiredBinder;
import com.warm.router.annotations.model.RouteInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 作者：warm
 * 时间：2019-07-20 15:56
 * 描述：
 */
public class Router {

    public static final Map<String, RouteInfo> mRouteInfoMap = new HashMap<>();
    public static final Map<String, AutowiredBinder> mBinderInfoMap = new HashMap<>();
    public static final Map<String, Interceptor> mInterceptorMap = new HashMap<>();
    public static Set<Interceptor> sGlobalInterceptors = new HashSet<>();

    static {
        init();
    }

    private static RouteClient sRouteClient = new RouteClient();

    public static void init() {

    }

    private static void loadGlobalInterceptors(String[] keys) {
        if (keys != null) {
            for (String key : keys) {
                sGlobalInterceptors.add(mInterceptorMap.get(key));
            }
        }
    }

    public static <T> void bind(T obj) {
        AutowiredBinder binder = mBinderInfoMap.get(obj.getClass().getName());
        //此处进行拦截
        if (binder != null) {
            binder.bind(obj);
        }
    }

    public static IRoute build(String path) {
        return sRouteClient.build(Uri.parse(path));
    }

    public static IRoute build(Uri uri) {
        return sRouteClient.build(uri);
    }

    public static IRoute build(Request request) {
        return sRouteClient.build(request);
    }

}
