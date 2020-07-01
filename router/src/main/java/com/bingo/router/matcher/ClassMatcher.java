package com.bingo.router.matcher;

import android.content.Context;
import android.net.Uri;

import com.bingo.router.Request;
import com.bingo.router.Router;
import com.bingo.router.Const;

class ClassMatcher extends ExplicitMatcher {
    @Override
    public boolean match(Context context, Uri uri, Request request) {
        if (uri.getScheme() != null && uri.getScheme().equals(Router.URI_SCHEME)) {
            return true;
        }
        if (uri.getAuthority() != null && uri.getAuthority().equals(Router.URI_AUTHORITY)) {
            return true;
        }
        return uri.getPath() != null && Router.getRouteInfo(uri.getPath()) != null;
    }
}
