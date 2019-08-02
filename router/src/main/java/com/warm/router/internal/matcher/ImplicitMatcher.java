package com.warm.router.internal.matcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.warm.router.Request;

class ImplicitMatcher extends Matcher {

    @Override
    public boolean match(Context context, Uri uri, Request request) {
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(
                new Intent(Intent.ACTION_VIEW, uri), PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null) {
            // bundle parser
            if (uri.getQuery() != null) {
                putParameter(uri, request.getExtra());
            }
            return true;
        }
        return false;
    }

    @Override
    public Object generate(Context context, Uri uri, Class<?> clazz) {
        return new Intent(Intent.ACTION_VIEW);
    }
}
