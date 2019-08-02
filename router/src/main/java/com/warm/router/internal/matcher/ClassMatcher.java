package com.warm.router.internal.matcher;

import android.content.Context;
import android.net.Uri;

import com.warm.router.Request;

 class ClassMatcher extends ExplicitMatcher {
    @Override
    boolean match(Context context, Uri uri, Request request) {
        return false;
    }
}
