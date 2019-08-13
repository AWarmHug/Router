package com.warm.demo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
                startActivity(new Intent(MainActivity.this,DetailActivity.class));
//                Router.build("test/detail")
//                        .with("type",1)
//                        .start(MainActivity.this);
            }
        });
        mBinding.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.build("https://www.jianshu.com/p/d57abb5b87f3")
                        .with("type",1)
                        .start(MainActivity.this);
            }
        });
        mBinding.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.build("myapp://reader.app/appweb?id=10001")
                        .with("type",1)
                        .start(MainActivity.this);
            }
        });
    }
}
