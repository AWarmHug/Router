package com.warm.demo.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.warm.demo.R;
import com.warm.demo.databinding.LoginActivityBinding;
import com.warm.demo.login.rx.RxLogin;
import com.warm.router.annotations.Route;

@Route(value = "login")
public class LoginActivity extends AppCompatActivity {

    public static final String NAME_IS_LOGIN = "isLogin";

    private LoginActivityBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        mBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxLogin.isLogin = true;
                Intent intent = getIntent();
                intent.putExtra(NAME_IS_LOGIN, true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
