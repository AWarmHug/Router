package com.warm.demo;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.warm.demo.databinding.ActivityMainBinding;
import com.warm.router.Router;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.init();
            }
        });
        mBinding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.startActivity("test/detail")
                        .putExtra("type",1)
                        .by(MainActivity.this);
            }
        });
    }
}
