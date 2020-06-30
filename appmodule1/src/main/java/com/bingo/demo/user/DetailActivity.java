package com.bingo.demo.user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.News;
import com.bingo.demo.approuterpath.User;
import com.bingo.demo.data.PayInfo;
import com.bingo.demo.data.UserInfo;
import com.bingo.demo.databinding.ActivityUserDetailBinding;
import com.bingo.demo.news.HomeFragment;
import com.bingo.router.Router;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

import java.util.List;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route(pathClass = User.Detail.class, interceptors = {"LoginInterceptor"})
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
        HomeFragment fragment = (HomeFragment) Router.newRequest(News.Home.class).build().getFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
        mBinding.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
