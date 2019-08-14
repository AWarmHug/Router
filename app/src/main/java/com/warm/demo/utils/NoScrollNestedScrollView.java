package com.warm.demo.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Space;

import com.warm.demo.R;

/**
 * Created by dingli.li on 2019/7/4
 */
public class NoScrollNestedScrollView extends NestedScrollView {


    public NoScrollNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public NoScrollNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Space space=findViewById(R.id.spacer);
            if (space != null) {
                int[] location = new int[2];
                space.getLocationInWindow(location);

                int height = space.getMeasuredHeight();
                int width = space.getMeasuredWidth();
                int upX = (int) ev.getRawX();
                int upY = (int) ev.getRawY();

                Rect rect = new Rect(location[0], location[1], width, location[1] + height);
                if (rect.contains(upX, upY)) {
                    return false;
                }
            }
        }
        return super.onTouchEvent(ev);
    }
}