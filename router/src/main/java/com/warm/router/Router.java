package com.warm.router;

import android.net.Uri;

import com.warm.router.annotations.model.AutowiredBinder;
import com.warm.router.annotations.model.Const;
import com.warm.router.annotations.model.RouteInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：warm
 * 时间：2019-07-20 15:56
 * 描述：
 */
public class Router {

    public static final Map<String, RouteInfo> mRouteInfoMap = new HashMap<>();
    private static Map<String, AutowiredBinder> mBinderInfoMap = new HashMap<>();

    private static RouteClient sRouteClient = new RouteClient();

    public static void init() {
        try {




            Class<?> routerLoader = Class.forName(Const.LOADER_PKG + Const.DOT + Const.ROUTER_LOADER_CLASS_NAME);
            Method routerMethodLoad = routerLoader.getMethod(Const.METHOD_LODE, Map.class);
            routerMethodLoad.invoke(routerLoader.newInstance(), mRouteInfoMap);


            Class<?> binderLoader = Class.forName(Const.LOADER_PKG + Const.DOT + Const.BINDER_LOADER_CLASS_NAME);
            Method binderMethodLoad = binderLoader.getMethod(Const.METHOD_LODE, Map.class);
            binderMethodLoad.invoke(binderLoader.newInstance(), mBinderInfoMap);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
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
