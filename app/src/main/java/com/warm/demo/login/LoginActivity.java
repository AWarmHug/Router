package com.warm.demo.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.warm.demo.R;
import com.warm.demo.databinding.LoginActivityBinding;

public class LoginActivity extends AppCompatActivity {

    public static final String NAME_IS_LOGIN = "isLogin";

    private LoginActivityBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.login_activity);

    }
}
