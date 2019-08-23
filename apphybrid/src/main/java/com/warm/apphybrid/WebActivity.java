package com.warm.apphybrid;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.warm.apphybrid.databinding.ActivityWebBinding;
import com.warm.router.annotations.Route;


@Route(value = "apphybrid/web")
public class WebActivity extends AppCompatActivity {

    private ActivityWebBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        mBinding.web.loadUrl("file:///android_asset/scheme.html");
    }
}
