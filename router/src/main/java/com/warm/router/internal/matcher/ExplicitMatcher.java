package com.warm.router.internal.matcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

 abstract class ExplicitMatcher extends Matcher {


    @Override
    public Object generate(Context context, Uri uri, Class<?> clazz) {
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
