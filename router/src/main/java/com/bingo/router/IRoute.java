package com.bingo.router;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface IRoute {

    Request newRequest(Uri uri);

    Request newRequest(Request request);

    @Nullable
    Fragment getFragment(Context obj);

    void startBy(Object obj);

}
