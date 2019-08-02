package com.warm.router;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Request implements Serializable {
    private Uri mUri;

    private Bundle mExtra;

    private Bundle mOptionsBundle;

    private int mRequestCode;

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


    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }


}
