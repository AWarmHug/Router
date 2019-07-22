package com.warm.demo;


import com.warm.router.annotations.model.RouteInfo;

import java.util.Map;

/**
 * 作者：warm
 * 时间：2019-07-20 15:42
 * 描述：
 */
public class Ut {

    public static void assembly(Map<String, RouteInfo> map, String path, RouteInfo routeInfo){
        map.put(path,routeInfo);
    }

}
