package com.warm.router;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface Interceptor {

    void intercept(Chain chain);


    interface Chain {

        Request request();

        void proceed(Request request);

        @NonNull
        Context getContext();

        @Nullable
        Fragment getFragment();


    }


}
