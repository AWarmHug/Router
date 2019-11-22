package com.bingo.demo.meizi;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.Meizi;
import com.bingo.demo.databinding.ActivityMeiziBinding;
import com.bingo.router.annotations.Route;

import java.util.Random;

@Route(pathClass = Meizi.class)
public class MeiziActivity extends AppCompatActivity {
    private static final String TAG = "MeiziActivityTag";

    private ActivityMeiziBinding mBinding;
    private MeiziActivityViewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(this).get(MeiziActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_meizi);
        mBinding.setVm(mModel);
        mBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.loadMeizi(1, MeiziActivity.this);
            }
        });
    }
}
