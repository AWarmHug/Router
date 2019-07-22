package com.warm.demo.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.warm.demo.R;
import com.warm.router.annotations.Route;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route("test/user/detail")
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }
}
