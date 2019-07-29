package com.warm.demo.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.warm.demo.R;
import com.warm.demo.databinding.ActivityUserDetailBinding;
import com.warm.demo.news.HomeFragment;
import com.warm.router.Router;
import com.warm.router.annotations.Route;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route("test/user/detail")
public class DetailActivity extends AppCompatActivity {
    private ActivityUserDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        HomeFragment fragment = (HomeFragment) Router.newInstance("news/home").by(this);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();

    }
}