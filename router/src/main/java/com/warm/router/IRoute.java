package com.warm.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface IRoute {

    IRoute build(Uri uri);

    IRoute build(Request request);

    IRoute with(String key,Object value);

    IRoute withRequestCode(int requestCode);

    @Nullable
    Intent getIntent(Object obj);

    @Nullable
    Fragment getFragment(Object obj);

    void start(Object obj);

}
