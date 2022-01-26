package com.bingo.router.internal;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.bingo.router.Const;
import com.bingo.router.Loader;
import com.bingo.router.RouteInfo;
import com.bingo.router.Utils;

import java.util.HashMap;
import java.util.Map;

public class RouteMapper implements Mapper<String, RouteInfo> {
    private Map<String, Loader<String, RouteInfo>> mMap;
    private Map<String, RouteInfo> mRouteMap;

    public RouteMapper() {
        mMap = new HashMap<>();
        mRouteMap = new HashMap<>();

    }

    @Nullable
    @Override
    public RouteInfo get(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        RouteInfo routeInfo = mRouteMap.get(path);
        if (routeInfo == null) {
            String group;
            if (Utils.countStr(path, '/') < 2) {
                group = Const.DEFAULT_GROUP;
            }else {
                group = path.substring(1).split("/")[0];
            }
            Loader<String, RouteInfo> routeInfoLoader = mMap.get(group);
            if (routeInfoLoader != null) {
                routeInfoLoader.load(mRouteMap);
                return mRouteMap.get(path);
            }
        } else {
            return routeInfo;
        }
        return null;
    }
}
