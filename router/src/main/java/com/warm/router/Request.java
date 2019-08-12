package com.warm.router;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable {
    private Uri mUri;

    private Bundle mExtra;

    private Bundle mOptionsBundle;

    private int mRequestCode = -1;

    private List<Interceptor> mInterceptors = new ArrayList<>();

    public Request(Uri uri) {
        mUri = uri;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    @NonNull
    public Bundle getExtra() {
        if (mExtra == null) {
            mExtra = new Bundle();
            setExtra(mExtra);
        }
        return mExtra;
    }

    public void setExtra(Bundle extra) {
        mExtra = extra;
    }

    public Bundle getOptionsBundle() {
        return mOptionsBundle;
    }

    public void setOptionsBundle(Bundle optionsBundle) {
        mOptionsBundle = optionsBundle;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public void addInterceptor(Interceptor interceptor) {
        if (!mInterceptors.contains(interceptor)) {
            mInterceptors.add(interceptor);
        }
    }

    public void removeInterceptor(Interceptor interceptor) {
        mInterceptors.remove(interceptor);
    }

}
