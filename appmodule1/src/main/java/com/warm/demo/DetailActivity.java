package com.warm.demo;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.warm.demo.databinding.ActivityDetailBinding;
import com.warm.router.Router;
import com.warm.router.annotations.Parameter;
import com.warm.router.annotations.Route;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route("test/detail")
public class DetailActivity extends AppCompatActivity {
    @Parameter(name = "type")
    int type;
    @Parameter
    String[] name;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Router.bind(this);
        mBinding.tv.setText("type=" + type);
//        BottomSheetBehavior<NestedScrollView> behavior = BottomSheetBehavior.from(mBinding.scrollView);
//        behavior.setPeekHeight(128);
    }
}
