package com.bingo.demo.login.rx;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bingo.demo.utils.Lazy;

import io.reactivex.Observable;

public class RxLogin {

    static final String TAG = RxLogin.class.getSimpleName();
    public static boolean isLogin;
    private Lazy<RxLoginFragment> mLoginFragmentLazy;


    public RxLogin(@NonNull FragmentActivity activity) {
        this.mLoginFragmentLazy = this.getLazySingleton(activity.getSupportFragmentManager());
    }

    public RxLogin(@NonNull Fragment fragment) {
        this.mLoginFragmentLazy = this.getLazySingleton(fragment.getChildFragmentManager());
    }


    @NonNull
    private Lazy<RxLoginFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<RxLoginFragment>() {
            private RxLoginFragment mRxLoginFragment;

            public synchronized RxLoginFragment get() {
                if (this.mRxLoginFragment == null) {
                    this.mRxLoginFragment = RxLogin.this.getRxLoginFragment(fragmentManager);
                }
                return this.mRxLoginFragment;
            }
        };
    }


    private RxLoginFragment getRxLoginFragment(@NonNull FragmentManager fragmentManager) {
        RxLoginFragment rxLoginFragment = this.findRxLoginFragment(fragmentManager);
        boolean isNewInstance = rxLoginFragment == null;
        if (isNewInstance) {
            rxLoginFragment = new RxLoginFragment();
            fragmentManager.beginTransaction().add(rxLoginFragment, TAG).commitNow();
        }
        return rxLoginFragment;
    }

    private RxLoginFragment findRxLoginFragment(@NonNull FragmentManager fragmentManager) {
        return (RxLoginFragment) fragmentManager.findFragmentByTag(TAG);
    }


    public Observable<Boolean> login() {

        if (!isLogin) {
            mLoginFragmentLazy.get().login();
            return mLoginFragmentLazy.get().getSubject();
        } else {
            return Observable.just(true);
        }
    }
}
