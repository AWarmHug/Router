package com.bingo.demo.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.Login;
import com.bingo.demo.databinding.LoginActivityBinding;
import com.bingo.demo.login.rx.RxLogin;
import com.bingo.router.annotations.Route;

@Route(pathClass = Login.Logina.class)
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
