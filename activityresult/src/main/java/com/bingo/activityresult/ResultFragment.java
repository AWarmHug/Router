package com.bingo.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;

public class ResultFragment  extends Fragment {
    private static final String TAG = "ResultFragment";

    private SparseArray<OnResultCallback> mResultCallbackStorage = new SparseArray<>();
    private int mRequestCode = 200;
    private boolean mLogging;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    int buildRequestCode(OnResultCallback callback) {
        mResultCallbackStorage.put(++mRequestCode, callback);
        return mRequestCode;
    }

    void setResultCallback(OnResultCallback callback) {
        mResultCallbackStorage.put(++mRequestCode, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("LoginManager", "ResultFragment  onActivityResult");
        OnResultCallback callback = mResultCallbackStorage.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }

    void setLogging(boolean logging) {
        mLogging = logging;
    }

    void log(String message) {
        if (mLogging) {
            Log.d(TAG, message);
        }
    }
}
