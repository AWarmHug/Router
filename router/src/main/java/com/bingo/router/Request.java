package com.bingo.router;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bingo.router.annotations.model.RouteInfo;
import com.bingo.router.internal.matcher.Matcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable {
    private Uri mUri;

    private Bundle mExtras;

    private Bundle mOptionsBundle;

    private int mRequestCode = -1;

    private int mFlags;

    private String action = Intent.ACTION_VIEW;

    private List<Interceptor> mInterceptors = new ArrayList<>();

    public Request(Uri uri) {
        mUri = uri;
        mExtras = new Bundle();
    }

    public Uri getUri() {
        return mUri;
    }

    public Request setUri(Uri uri) {
        mUri = uri;
        return this;
    }

    @NonNull
    public Bundle getExtras() {
        return mExtras;
    }

    public Request setExtras(Bundle extra) {
        mExtras = extra;
        return this;
    }

    public Bundle getOptionsBundle() {
        return mOptionsBundle;
    }

    public Request setOptionsBundle(Bundle optionsBundle) {
        mOptionsBundle = optionsBundle;
        return this;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public Request setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public Request addInterceptor(Interceptor interceptor) {
        if (!mInterceptors.contains(interceptor)) {
            mInterceptors.add(interceptor);
        }
        return this;
    }

    public Request removeInterceptor(Interceptor interceptor) {
        mInterceptors.remove(interceptor);
        return this;
    }

    public int getFlags() {
        return mFlags;
    }

    public Request setFlags(int flags) {
        mFlags = flags;
        return this;
    }

    public Request addFlags(int flags) {
        mFlags |= flags;
        return this;

    }

    public String getAction() {
        return action;
    }

    public Request setAction(String action) {
        this.action = action;
        return this;
    }

    @Nullable
    public RouteInfo getRouteInfo() {
        return Router.mRouteInfoMap.get(getUri().getPath());
    }

    @Nullable
    public Class<?> getTarget() {
        RouteInfo info = getRouteInfo();
        if (info != null) {
            return info.getTarget();
        }
        return null;
    }


    public Request putString(@Nullable String key, @Nullable String value) {
        mExtras.putString(key, value);
        return this;
    }

    public Request putBoolean(@Nullable String key, boolean value) {
        mExtras.putBoolean(key, value);
        return this;
    }

    public Request putShort(@Nullable String key, short value) {
        mExtras.putShort(key, value);
        return this;
    }


    public Request putInt(@Nullable String key, int value) {
        mExtras.putInt(key, value);
        return this;
    }


    public Request putLong(@Nullable String key, long value) {
        mExtras.putLong(key, value);
        return this;
    }


    public Request putDouble(@Nullable String key, double value) {
        mExtras.putDouble(key, value);
        return this;
    }


    public Request putByte(@Nullable String key, byte value) {
        mExtras.putByte(key, value);
        return this;
    }


    public Request putChar(@Nullable String key, char value) {
        mExtras.putChar(key, value);
        return this;
    }


    public Request putFloat(@Nullable String key, float value) {
        mExtras.putFloat(key, value);
        return this;
    }

    public Request putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        mExtras.putCharSequence(key, value);
        return this;
    }


    public Request putParcelable(@Nullable String key, @Nullable Parcelable value) {
        mExtras.putParcelable(key, value);
        return this;
    }


    public Request putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        mExtras.putParcelableArray(key, value);
        return this;
    }

    public Request putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        mExtras.putParcelableArrayList(key, value);
        return this;
    }


    public Request putSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        mExtras.putSparseParcelableArray(key, value);
        return this;
    }


    public Request putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mExtras.putIntegerArrayList(key, value);
        return this;
    }


    public Request putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mExtras.putStringArrayList(key, value);
        return this;
    }


    public Request putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        mExtras.putCharSequenceArrayList(key, value);
        return this;
    }

    public Request putSerializable(@Nullable String key, @Nullable Serializable value) {
        mExtras.putSerializable(key, value);
        return this;
    }

    public Request putStringArray(@Nullable String key, @Nullable String[] value) {
        mExtras.putStringArray(key, value);
        return this;
    }

    public Request putLongArray(@Nullable String key, @Nullable long[] value) {
        mExtras.putLongArray(key, value);
        return this;
    }

    public Request putByteArray(@Nullable String key, @Nullable byte[] value) {
        mExtras.putByteArray(key, value);
        return this;
    }

    public Request putShortArray(@Nullable String key, @Nullable short[] value) {
        mExtras.putShortArray(key, value);
        return this;
    }

    public Request putCharArray(@Nullable String key, @Nullable char[] value) {
        mExtras.putCharArray(key, value);
        return this;
    }

    public Request putFloatArray(@Nullable String key, @Nullable float[] value) {
        mExtras.putFloatArray(key, value);
        return this;
    }

    public Request putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        mExtras.putCharSequenceArray(key, value);
        return this;
    }

    public Request putBundle(@Nullable String key, @Nullable Bundle value) {
        mExtras.putBundle(key, value);
        return this;
    }

    public Request putBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            mExtras.putAll(bundle);
        }
        return this;
    }

    @RequiresApi(21)
    public Request put(PersistableBundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            mExtras.putAll(bundle);
        }
        return this;
    }

    public Request putParameter() {
        Matcher.putParameter(mUri, mExtras);
        mUri = Uri.parse(mUri.getPath().substring(1));
        return this;
    }

    public IRoute build() {
        return new RealRoute(this);
    }


}
