package com.warm.router;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.warm.router.annotations.model.RouteInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable {
    private Uri mUri;

    private Bundle mExtra;

    private Bundle mOptionsBundle;

    private int mRequestCode = -1;

    private int mFlags;

    private String action;

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

    public int getFlags() {
        return mFlags;
    }

    public void setFlags(int flags) {
        mFlags = flags;
    }

    public void addFlags(int flags) {
        mFlags |= flags;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Nullable
    public Class<?> getTarget() {
        RouteInfo info = Router.mRouteInfoMap.get(getUri().getPath());
        if (info != null) {
            return info.getTarget();
        }
        return null;
    }
}
