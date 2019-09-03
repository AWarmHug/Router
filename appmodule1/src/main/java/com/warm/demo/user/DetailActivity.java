package com.warm.demo.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.warm.demo.R;
import com.warm.demo.data.PayInfo;
import com.warm.demo.data.UserInfo;
import com.warm.demo.databinding.ActivityUserDetailBinding;
import com.warm.demo.news.HomeFragment;
import com.warm.router.Router;
import com.warm.router.annotations.Parameter;
import com.warm.router.annotations.Route;

import java.util.List;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route(value = "test/user/detail", interceptors = {"LoginInterceptor"})
public class DetailActivity extends AppCompatActivity {
    @Parameter
    long id;
    @Parameter
    long[] ids;
    @Parameter
    List<Long> idList;
    @Parameter
    String from;
    @Parameter
    String[] froms;
    @Parameter
    List<String> fromList;
    @Parameter
    UserInfo mUserInfo;
    @Parameter
    PayInfo mPayInfo;
    private ActivityUserDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.bind(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        mBinding.tvInfo.setText(String.valueOf(id));
        HomeFragment fragment = (HomeFragment) Router.build("news/home").getFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();

    }
}
