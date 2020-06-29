package com.bingo.router.internal.matcher;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bingo.router.Request;

abstract class ExplicitMatcher extends Matcher {


    @Override
    public Object generate(Context context, Uri uri, Request request) {
        Class<?> clazz = request.getTarget();

        if (clazz == null) {
            return null;
        }
        if (Activity.class.isAssignableFrom(clazz)) {
            return new Intent(context, clazz);
        } else if (Service.class.isAssignableFrom(clazz)) {
            return new Intent(context, clazz);
        } else if (Fragment.class.isAssignableFrom(clazz)) {
            if (context instanceof FragmentActivity && clazz.getClassLoader() != null) {
                FragmentActivity activity = (FragmentActivity) context;
                return activity.getSupportFragmentManager().getFragmentFactory().instantiate(clazz.getClassLoader(), clazz.getName());
            } else {
                return Fragment.instantiate(context, clazz.getName());
            }
        }
        return null;
    }
}
