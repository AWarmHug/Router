package com.warm.router;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface Interceptor {

    void intercept(Chain chain);


    interface Chain {

        Request request();

        void proceed(Request request);

        @Nullable
        Context getContext();

        @Nullable
        Fragment getFragment();


    }


}
