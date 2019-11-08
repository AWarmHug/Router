package com.bingo.router.internal.matcher;

import android.content.Context;
import android.net.Uri;

import com.bingo.router.Request;
import com.bingo.router.Router;

class ClassMatcher extends ExplicitMatcher {
    @Override
    public boolean match(Context context, Uri uri, Request request) {
        return Router.mRouteInfoMap.containsKey(uri.getPath());
    }
}
