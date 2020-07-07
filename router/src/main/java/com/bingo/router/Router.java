package com.bingo.router;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.bingo.router.annotations.Route;

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
    public static final String URI_SCHEME = "app";
    public static final String URI_AUTHORITY = "route";
    public static final String URI_SCHEME_AUTHORITY = URI_SCHEME + "://" + URI_AUTHORITY + "/";


    private static final Map<String, Loader<RouteInfo>> mGroupMap = new HashMap<>();
    private static final Map<String, RouteInfo> mRouteMap = new HashMap<>();
    public static final Map<String, Interceptor> mInterceptorMap = new HashMap<>();
    public static Set<Interceptor> sGlobalInterceptors = new HashSet<>();


    static {
        init();
    }

    public static void init() {

    }

    @Nullable
    public static RouteInfo getRouteInfo(@Nullable String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        RouteInfo routeInfo = mRouteMap.get(path);
        if (routeInfo == null) {
            String group = path.substring(1).split("/")[0];
            Loader<RouteInfo> routeInfoLoader = mGroupMap.get(group);
            if (routeInfoLoader != null) {
                routeInfoLoader.load(mRouteMap);
                return mRouteMap.get(path);
            }
        } else {
            return routeInfo;
        }
        return null;
    }

    private static void loadGlobalInterceptors(String[] keys) {
        if (keys != null) {
            for (String key : keys) {
                sGlobalInterceptors.add(mInterceptorMap.get(key));
            }
        }
    }

    public static <T> void bind(T obj) {
        Route route = obj.getClass().getAnnotation(Route.class);
        if (route == null) {
            throw new RuntimeException("请添加Route注解");
        }
        RouteInfo routeInfo = getRouteInfo(Utils.getPath(route));
        if (routeInfo == null) {
            throw new RuntimeException("获取不到路由信息");
        }
        AutowiredBinder binder = routeInfo.getAutowiredBinder();
        //此处进行拦截
        if (binder != null) {
            binder.bind(obj);
        }
    }

    public static Request newRequest(Class<?> pathClass) {
        return newRequest(Utils.pathByPathClass(pathClass));
    }

    public static Request newRequest(String path) {

        return newRequest(Uri.parse(path));
    }


    public static Request newRequest(Uri uri) {
        if (TextUtils.isEmpty(uri.getScheme()) || TextUtils.isEmpty(uri.getAuthority())) {
            String uriStr = uri.toString();
            if (!uriStr.startsWith(Router.URI_SCHEME_AUTHORITY)) {
                uri = Uri.parse(Router.URI_SCHEME_AUTHORITY + uriStr);
            }
        }
        return new Request(uri);
    }

    public static IRoute build(String path) {
        return newRequest(path).build();
    }

    public static IRoute build(Uri uri) {
        return newRequest(uri).build();
    }

    private static Map<Class, Object> map = new HashMap<>();

    public static <T> T create(Class<T> clazz) {

        if (map.get(clazz) == null) {
            try {
                T t = (T) Class.forName(clazz.getName() + "Impl").newInstance();
                map.put(clazz, t);
                return t;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return (T) map.get(clazz);
    }

}
