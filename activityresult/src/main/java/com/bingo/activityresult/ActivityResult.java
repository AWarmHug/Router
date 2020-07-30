package com.bingo.activityresult;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class ActivityResult {

    private static final String TAG = ActivityResult.class.getSimpleName();

    private FragmentManager mFragmentManager;
    private Lazy<ResultFragment> mResultFragment;
    private FragmentActivity mFragmentActivity;

    public ActivityResult(FragmentActivity activity) {
        mFragmentActivity = activity;
        mFragmentManager = activity.getSupportFragmentManager();
        mResultFragment = getLazySingleton();
    }

    public ActivityResult(Fragment fragment) {
        mFragmentActivity = fragment.getActivity();
        mFragmentManager = fragment.getChildFragmentManager();
        mResultFragment = getLazySingleton();
    }

    /**
     * Convenient method to start activity for result.
     */
    public void startActivityForResult(Intent intent, OnResultCallback callback) {
        ResultFragment resultFragment = mResultFragment.get();
        if(resultFragment.isAdded()) {
            int requestCode = resultFragment.buildRequestCode(callback);
            resultFragment.startActivityForResult(intent, requestCode);
        }
    }

    @NonNull
    private Lazy<ResultFragment> getLazySingleton() {
        return new Lazy<ResultFragment>() {
            private ResultFragment permissionsFragment;

            @Override
            public synchronized ResultFragment get() {
                if (permissionsFragment == null) {
                    permissionsFragment = getResultFragment();
                    permissionsFragment.setLogging(true);
                }
                return permissionsFragment;
            }
        };
    }

    private ResultFragment getResultFragment() {
        ResultFragment permissionsFragment = (ResultFragment) mFragmentManager.findFragmentByTag(TAG);
        boolean isNewInstance = permissionsFragment == null;
        if (isNewInstance) {
            permissionsFragment = new ResultFragment();
            mFragmentManager
                    .beginTransaction()
                    .add(permissionsFragment,TAG)
                    .commitNowAllowingStateLoss();
        }
        return permissionsFragment;
    }

    @FunctionalInterface
    interface Lazy<V> {
        V get();
    }
}
