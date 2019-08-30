package com.warm.router.utils;

import android.os.Bundle;

public class BundleUtils {

    public static <T> T get(Bundle bundle, String key, T defaultValue) {
        if (bundle == null) {
            return defaultValue;
        }
        Object o = bundle.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {



            return (T) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }
}
