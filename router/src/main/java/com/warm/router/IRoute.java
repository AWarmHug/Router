package com.warm.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

public interface IRoute {

    Request newRequest(Uri uri);

    Request newRequest(Request request);

    @Nullable
    Fragment getFragment(Context obj);

    void start(Object obj);

}
