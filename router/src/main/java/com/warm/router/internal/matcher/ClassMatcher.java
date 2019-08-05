package com.warm.router.internal.matcher;

import android.content.Context;
import android.net.Uri;

import com.warm.router.Request;
import com.warm.router.Router;

class ClassMatcher extends ExplicitMatcher {
    @Override
    public boolean match(Context context, Uri uri, Request request) {
        return Router.mRouteInfoMap.containsKey(uri.getPath());
    }
}
