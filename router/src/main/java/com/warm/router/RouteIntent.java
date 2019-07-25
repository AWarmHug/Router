package com.warm.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.warm.router.annotations.model.RouteInfo;

public class RouteIntent extends Intent {
    private RouteInfo mRouteInfo;

    public RouteIntent(RouteInfo routeInfo) {
        mRouteInfo = routeInfo;
    }

    @Override
    public RouteIntent putExtra(String name, String value) {
        return (RouteIntent) super.putExtra(name, value);
    }

    @Override
    public RouteIntent putExtra(String name, int value) {
        return (RouteIntent) super.putExtra(name, value);
    }

    @Override
    public RouteIntent putExtra(String name, long value) {
        return (RouteIntent) super.putExtra(name, value);
    }


    public void by(Context context) {
        setClass(context, mRouteInfo.getTarget());
        context.startActivity(this);
    }
}
