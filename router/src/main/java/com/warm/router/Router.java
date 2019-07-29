package com.warm.router;

import android.support.v4.app.Fragment;

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

    private static Map<String, RouteInfo> mRouteInfoMap = new HashMap<>();


    public static void init() {
        String classPkg = Const.ROUTE_PKG + Const.DOT + Const.ROUTE_CLASS_NAME;
        try {
            Class<?> route = Class.forName(classPkg);
            Method methodLoad = route.getMethod(Const.METHOD_LODE, Map.class);
            methodLoad.invoke(route.newInstance(), mRouteInfoMap);
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

    public static void bind(Object obj) {

    }


    public static RouteIntent startActivity(String path) {
        //此处进行拦截
        return new RouteIntent(mRouteInfoMap.get(path));
    }

    public static FragmentBundle newInstance(String path){
        //此处进行拦截
        return new FragmentBundle(mRouteInfoMap.get(path));
    }

}
