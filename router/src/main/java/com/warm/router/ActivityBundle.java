package com.warm.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.warm.router.annotations.model.RouteInfo;

public class ActivityBundle {

    private RouteInfo mRouteInfo;

    private boolean mForResult;

    private Bundle mBundle;

    public ActivityBundle(RouteInfo routeInfo) {
        mRouteInfo = routeInfo;

        mBundle = new Bundle();
    }


    public ActivityBundle putExtra(String name, String value) {
        mBundle.putString(name, value);
        return this;
    }

    public ActivityBundle putExtra(String name, int value) {
        mBundle.putInt(name, value);
        return this;
    }

    public ActivityBundle putExtra(String name, long value) {
        mBundle.putLong(name, value);
        return this;
    }


    public void by(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, mRouteInfo.getTarget());
        ContextCompat.startActivity(context, intent, mBundle);
    }
}
