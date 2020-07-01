package com.bingo.router.matcher;

import android.content.Context;
import android.net.Uri;

import com.bingo.router.Request;

class BrowserMatcher extends ImplicitMatcher {
    @Override
    public boolean match(Context context, Uri uri, Request request) {
        return uri.toString().toLowerCase().startsWith("http://") || uri.toString().toLowerCase().startsWith("https://");
    }
}
