package com.bingo.router.internal;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RoutePathMapper implements Mapper<Class<?>, Class<?>> {
    private Map<Class<?>, Class<?>> mMap;

    public RoutePathMapper() {
        mMap = new HashMap<>();
    }

    @Nullable
    @Override
    public Class<?> get(Class<?> aClass) {
        return mMap.get(aClass);
    }
}
