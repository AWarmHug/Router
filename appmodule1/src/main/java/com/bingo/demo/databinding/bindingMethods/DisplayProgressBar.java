package com.bingo.demo.databinding.bindingMethods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DisplayProgressBar extends ProgressBar {
    private OnViewChangeListener onViewChangeListener;


    public DisplayProgressBar(Context context) {
        super(context);
    }

    public DisplayProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DisplayProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DisplayProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setOnViewChangeListener(OnViewChangeListener onViewChangeListener) {
        this.onViewChangeListener = onViewChangeListener;
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (onViewChangeListener!=null){
            onViewChangeListener.onChange();
        }

    }

    public interface OnViewChangeListener {
        void onChange();
    }

}
