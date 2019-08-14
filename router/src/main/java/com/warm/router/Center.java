package com.warm.router;

import com.warm.router.annotations.model.AutowiredBinder;
import com.warm.router.annotations.model.Loader;
import com.warm.router.annotations.model.RouteInfo;

import java.util.HashMap;
import java.util.Map;

public class Center {

    public Map<String, Loader<RouteInfo>> mRouteInfos=new HashMap<>();

    public Map<String, Loader<AutowiredBinder>> mBinderInfos=new HashMap<>();





}
