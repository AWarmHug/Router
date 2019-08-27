package com.warm.router;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;

import com.warm.router.annotations.model.RouteInfo;
import com.warm.router.internal.RouteChain;
import com.warm.router.internal.chain.FragmentInterceptor;
import com.warm.router.internal.chain.IntentInterceptor;
import com.warm.router.internal.matcher.Matcher;
import com.warm.router.internal.matcher.MatcherCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RouteClient implements IRoute {

    private Request mRequest;

    @Override
    public IRoute build(Uri uri) {
        return build(new Request(uri));
    }

    @Override
    public IRoute build(Request request) {
        mRequest = request;
        return this;
    }

    @Override
    public IRoute put(String key, Object value) {
        Bundle bundle = mRequest.getExtra();
        if (value instanceof Bundle) {
            bundle.putBundle(key, (Bundle) value);
        } else if (value instanceof Byte) {
            bundle.putByte(key, (byte) value);
        } else if (value instanceof Short) {
            bundle.putShort(key, (short) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (int) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (long) value);
        } else if (value instanceof Character) {
            bundle.putChar(key, (char) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (boolean) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key, (float) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key, (double) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof CharSequence) {
            bundle.putCharSequence(key, (CharSequence) value);
        } else if (value instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) value);
        } else if (value instanceof short[]) {
            bundle.putShortArray(key, (short[]) value);
        } else if (value instanceof int[]) {
            bundle.putIntArray(key, (int[]) value);
        } else if (value instanceof long[]) {
            bundle.putLongArray(key, (long[]) value);
        } else if (value instanceof char[]) {
            bundle.putCharArray(key, (char[]) value);
        } else if (value instanceof boolean[]) {
            bundle.putBooleanArray(key, (boolean[]) value);
        } else if (value instanceof float[]) {
            bundle.putFloatArray(key, (float[]) value);
        } else if (value instanceof double[]) {
            bundle.putDoubleArray(key, (double[]) value);
        } else if (value instanceof String[]) {
            bundle.putStringArray(key, (String[]) value);
        } else if (value instanceof CharSequence[]) {
            bundle.putCharSequenceArray(key, (CharSequence[]) value);
        } else if (value instanceof IBinder) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bundle.putBinder(key, (IBinder) value);
            } else {
                Log.d("aaa", "putBinder() requires api 18.");
            }
        } else if (value instanceof SparseArray) {
            bundle.putSparseParcelableArray(key, (SparseArray<? extends Parcelable>) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) value);
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        } else {
            Log.d("aaa", "Unknown object type: " + value.getClass().getName());
        }

        return this;
    }


    @Override
    public IRoute put(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            Bundle extras = mRequest.getExtra();
            extras.putAll(bundle);
            mRequest.setExtra(extras);
        }
        return this;
    }

    @RequiresApi(21)
    @Override
    public IRoute put(PersistableBundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            Bundle extras = mRequest.getExtra();
            extras.putAll(bundle);
            mRequest.setExtra(extras);
        }
        return this;
    }

    @Override
    public IRoute addFlags(int flags) {
        mRequest.addFlags(flags);
        return this;
    }


    @Override
    public IRoute setRequestCode(int requestCode) {
        mRequest.setRequestCode(requestCode);
        return this;
    }

    @Nullable
    @Override
    public Fragment getFragment(Context context) {
        //针对Fragment进行匹配
        //添加全局拦截器
        List<Interceptor> interceptors = new ArrayList<>(Router.sGlobalInterceptors);
        //添加针对拦截器
        RouteInfo info = Router.mRouteInfoMap.get(mRequest.getUri().getPath());
        if (info != null && info.getInterceptorKeys() != null) {
            for (String key : info.getInterceptorKeys()) {
                interceptors.add(Router.mInterceptorMap.get(key));
            }
        }

        if (!mRequest.getInterceptors().isEmpty()) {
            interceptors.addAll(mRequest.getInterceptors());
        }

        interceptors.add(new FragmentInterceptor());

        RouteChain chain = new RouteChain(context, mRequest, interceptors);
        chain.proceed(mRequest);

        Fragment fragment = null;
        for (Matcher matcher : MatcherCenter.sMatcher) {
            if (matcher.match(context, mRequest.getUri(), mRequest)) {
                Object target = matcher.generate(context, mRequest.getUri(), mRequest);
                if (target instanceof Fragment) {
                    fragment = (Fragment) target;
                }
                break;
            }
        }
        if (fragment != null) {
            fragment.setArguments(mRequest.getExtra());
        }
        return fragment;
    }

    @Override
    public void start(final Object obj) {

        //添加全局拦截器
        List<Interceptor> interceptors = new ArrayList<>(Router.sGlobalInterceptors);
        //添加针对拦截器
        final RouteInfo info = Router.mRouteInfoMap.get(mRequest.getUri().getPath());
        if (info != null && info.getInterceptorKeys() != null) {
            for (String key : info.getInterceptorKeys()) {
                interceptors.add(Router.mInterceptorMap.get(key));
            }
        }

        if (!mRequest.getInterceptors().isEmpty()) {
            interceptors.addAll(mRequest.getInterceptors());
        }


        IntentInterceptor intentInterceptor = new IntentInterceptor();

        interceptors.add(intentInterceptor);

        RouteChain chain = new RouteChain(obj, mRequest, interceptors);
        chain.proceed(mRequest);

    }


}
