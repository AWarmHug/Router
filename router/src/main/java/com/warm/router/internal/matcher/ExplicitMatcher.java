package com.warm.router.internal.matcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;

import com.warm.router.Request;

abstract class ExplicitMatcher extends Matcher {


    @Override
    public Object generate(Context context, Uri uri, Request request) {
        Class<?> clazz = request.getTarget();

        if (clazz == null) {
            return null;
        }
        if (Activity.class.isAssignableFrom(clazz)) {
            return new Intent(context, clazz);
        } else if (Fragment.class.isAssignableFrom(clazz)) {
            return Fragment.instantiate(context, clazz.getName());
        }
        return null;
    }
}
