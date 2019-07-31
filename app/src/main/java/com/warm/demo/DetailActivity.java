package com.warm.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.warm.demo.databinding.ActivityDetailBinding;
import com.warm.router.Router;
import com.warm.router.annotations.Autowired;
import com.warm.router.annotations.Route;

import java.lang.reflect.Array;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route("test/detail")
public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding mBinding;


    @Autowired(name = "type")
    int type;

    @Autowired
    String[] name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Router.bind(this);
        mBinding.tv.setText("type=" + type);
    }
}
