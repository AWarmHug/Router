package com.bingo.demo.meizi;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.Meizi;
import com.bingo.demo.databinding.ActivityMeiziBinding;
import com.bingo.router.annotations.Route;

@Route(pathClass = Meizi.class)
public class MeiziActivity extends AppCompatActivity {

    private ActivityMeiziBinding mBinding;
    private MeiziActivityViewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel= ViewModelProviders.of(this).get(MeiziActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_meizi);
    }
}
