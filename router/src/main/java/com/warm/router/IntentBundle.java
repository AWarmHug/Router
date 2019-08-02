package com.warm.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.warm.router.annotations.model.RouteInfo;

public class IntentBundle {

    private RouteInfo mRouteInfo;

    private Integer mRequestCode;

    private Bundle mBundle;

    public IntentBundle(RouteInfo routeInfo) {
        this(routeInfo, null);
    }

    public IntentBundle(RouteInfo routeInfo, Integer requestCode) {
        mRouteInfo = routeInfo;
        mBundle = new Bundle();
        mRequestCode = requestCode;
    }


    public IntentBundle putExtra(String name, String value) {
        mBundle.putString(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, int value) {
        mBundle.putInt(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, long value) {
        mBundle.putLong(name, value);
        return this;
    }


    public void by(Context context) {
        Intent intent = new Intent();
        intent.putExtras(mBundle);
        intent.setClass(context, mRouteInfo.getTarget());
        if (mRequestCode == null) {


            ActivityCompat.startActivity(context, intent, null);
        } else {
            if (context instanceof Activity) {
                ActivityCompat.startActivityForResult((Activity) context, intent, mRequestCode, mBundle);
            }
        }
    }
}
