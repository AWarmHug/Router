package com.warm.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

public interface IRoute {

    IRoute build(Uri uri);

    IRoute build(Request request);

    IRoute put(String key, Object value);

    IRoute put(Bundle bundle);

    @RequiresApi(21)
    IRoute put(PersistableBundle bundle);

    IRoute addFlags(int flags);

    IRoute setRequestCode(int requestCode);

    @Nullable
    Fragment getFragment(Context obj);

    void start(Object obj);

}
