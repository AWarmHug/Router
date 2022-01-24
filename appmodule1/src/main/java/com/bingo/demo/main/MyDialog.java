package com.bingo.demo.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bingo.demo.R;

class MyDialog extends Dialog {

    private int buju;
    private LinearLayout linearLayout;

    public MyDialog(Context context, int theme, int buju) {
        super(context, theme);
        this.buju = buju;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(buju);
        linearLayout = (LinearLayout) findViewById(R.id.main);
    }

    @Override
    public void show() {
        super.show();
        animation(700);
    }

    public void animation(int mDuration) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
//                //设置view按照Y周旋转的过程，从-90°到0°
//                ObjectAnimator.ofFloat(this, "rotationY", -90, 0).setDuration(mDuration),
//                //设置view在X轴的位移过程
//                ObjectAnimator.ofFloat(this, "translationX", -300, 0).setDuration(mDuration),
//
//                //定义view的旋转过程，从1080度转到0度，旋转两圈
//                ObjectAnimator.ofFloat(this, "rotation", 1080, 720, 360, 0).setDuration(mDuration),
//                //定义view的透明度从全隐，到全显的过程，setDuration(mDuration)是设置动画时间
//                ObjectAnimator.ofFloat(this, "alpha", 0, 1).setDuration(mDuration * 3 / 2),
                //定义view基于scaleX的缩放过程
                ObjectAnimator.ofFloat(linearLayout, "scaleX", 0.1f, 0.5f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(linearLayout, "scaleY", 0.1f, 0.5f, 1).setDuration(mDuration)
        );
        animatorSet.start();
    }
}