package com.warm.router;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.warm.router.annotations.model.RouteInfo;

public class FragmentBundle {
    private RouteInfo mRouteInfo;
    private Bundle mBundle;

    public FragmentBundle(RouteInfo routeInfo) {
        mRouteInfo = routeInfo;
        mBundle = new Bundle();
    }

    public FragmentBundle putString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public Fragment by(Context context) {
        return Fragment.instantiate(context, mRouteInfo.getTarget().getName(), mBundle);
    }

}
