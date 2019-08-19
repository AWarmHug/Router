package com.warm

class Config {
    public static final String KEY_MODULE_NAME = "moduleName";

    static final String DOT = ".";
    static final String PKG_ROUTER = "com.warm.router";
    static final String LOADER_PKG = PKG_ROUTER + DOT + "loader";

    static final String ROUTER_LOADER_CLASS_NAME = "RouteLoader";
    static final String BINDER_LOADER_CLASS_NAME = "BinderLoader";
    static final String INTERCEPTOR_LOADER_CLASS_NAME = "InterceptorLoader";

    static final String METHOD_LODE = "load";

    static final String BINDER_CLASS_NAME = "AutowiredBinder";
    static final String METHOD_BIND = "bind";
}
