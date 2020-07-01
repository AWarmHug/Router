package com.bingo.router.matcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.bingo.router.Request;

class ImplicitMatcher extends Matcher {

    @Override
    public boolean match(Context context, Uri uri, Request request) {
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(
                new Intent(request.getAction(), uri), PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null) {
            // bundle parser
            if (uri.getQuery() != null) {
                putParameter(uri, request.getExtras());
            }
            return true;
        }
        return false;
    }

    @Override
    public Object generate(Context context, Uri uri, Request request) {
        return new Intent(request.getAction(), uri);
    }
}
