package com.warm.router.internal.matcher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.warm.router.Request;

public abstract class Matcher {

    public abstract boolean match(Context context, Uri uri, Request request);

    public abstract Object generate(Context context, Uri uri, Request request);

    protected void putParameter(Uri uri, Bundle bundle) {
        if (TextUtils.isEmpty(uri.getQuery())) {
            return;
        }
        if (uri.getQueryParameterNames().isEmpty()) {
            return;
        }
        for (String key : uri.getQueryParameterNames()) {
            String value = uri.getQueryParameter(key);
            bundle.putString(key, value);
        }
    }

}
