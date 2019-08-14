package com.warm.demo.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.warm.demo.R;

public class ToolbarBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private static final String TAG = "ToolbarBehavior";

    private int mLastNestedScrollDy;

    public ToolbarBehavior() {
    }

    public ToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        mLastNestedScrollDy = dy;

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.d(TAG, "onNestedScroll: dxConsumed=" + dxConsumed + "dyConsumed=" + dyConsumed);
        if (!(target instanceof NestedScrollView)) {
            return;
        }
        if (child.getBackground() == null) {
            return;
        }

        if (target.getScrollY() == 0) {
            child.getBackground().mutate().setAlpha(0);
            return;

        }
        if (target.getScrollY() <= (600 - child.getMeasuredHeight())) {
            float alpha = Math.abs(child.getScrollY() * 1.0f) / (600 - child.getMeasuredHeight());
            if (alpha > 1) {
                alpha = 1;
            }
            int a = (int) (alpha * 255);
            child.getBackground().mutate().setAlpha(a);
        } else {
            child.getBackground().mutate().setAlpha(255);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        Log.d(TAG, "onStopNestedScroll: "+mLastNestedScrollDy);

    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling: ");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling: "+velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);

    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, Toolbar child, int layoutDirection) {
        if (child.getBackground() != null) {
            child.getBackground().mutate().setAlpha(0);
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }
}
