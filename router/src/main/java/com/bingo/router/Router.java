package com.bingo.router;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;
import com.bingo.router.internal.RouteMapper;
import com.bingo.router.internal.RoutePathMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
    public static final String URI_SCHEME_AUTHORITY = URI_SCHEME + "://" + URI_AUTHORITY;


    private static RouteMapper sRouteMapper;
    private static RoutePathMapper sRoutePathMapper;

    public static final Map<String, Interceptor> mInterceptorMap = new HashMap<>();
    public static Set<Interceptor> sGlobalInterceptors = new HashSet<>();

    static {
        init();
    }

    public static void init() {

    }

    @Nullable
    public static RouteInfo getRouteInfo(@Nullable String path) {
        if (sRouteMapper==null){
            sRouteMapper=new RouteMapper();
        }
        return sRouteMapper.get(path);
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
                if (!uriStr.startsWith("/")) {
                    uriStr = "/" + uriStr;
                }
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

    public static <T> T create(Context context, Class<T> clazz) {
        return createTByReflex(context, clazz);
    }

    public static <T> T create(Fragment fragment, Class<T> clazz) {
        return createTByReflex(fragment, clazz);
    }

    /**
     * TODO: 2020/7/7动态代理无法获取参数的名字，注解必须写name
     */
    private static <T> T createTByProxy(Class<T> clazz) {
        T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d("******", "invoke: name=");
                Route route = method.getAnnotation(Route.class);
                Request request = Router.newRequest(Utils.getPath(route));
                Object obj = null;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Context || args[i] instanceof Fragment) {
                        obj = args[i];
                    } else {
                        String name;
                        Parameter parameter = (Parameter) method.getParameterAnnotations()[i][0];
                        if (!TextUtils.isEmpty(parameter.value())) {
                            name = parameter.value();
                        } else {
                            name = "";// TODO: 2020/7/7动态代理无法获取参数的名字，注解必须写name
                        }
                        if (args[i] instanceof String) {
                            request.putString(name, (String) args[i]);
                        }
                        Log.d("******", "invoke: name=" + name + "arg=" + args[i]);
                    }
                }
                if (method.getReturnType() == Request.class) {
                    return request;
                } else if (method.getReturnType() == IRoute.class) {
                    return request.build();
                } else if (method.getReturnType() == void.class) {
                    request.startBy(obj);
                }
                return null;
            }
        });
        return t;
    }

    /**
     * 注意防混淆
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T createTByReflex(Object obj, Class<T> clazz) {
        if (sRoutePathMapper == null) {
            sRoutePathMapper = new RoutePathMapper();
        }
        Class c = sRoutePathMapper.get(clazz);
        if (c == null) {
            return null;
        }
        T t = null;
        try {
            Class<?> parameterType = null;
            if (obj instanceof Context) {
                parameterType = Context.class;
            } else if (obj instanceof Fragment) {
                parameterType = Fragment.class;
            }
            t = (T) c.getConstructor(parameterType).newInstance(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return t;
    }

}
