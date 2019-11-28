package com.bingo.demo.lifecycle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bingo.demo.R;
import com.bingo.demo.approuterpath.Lifecycle;
import com.bingo.demo.databinding.ActivityLifecycleBinding;
import com.bingo.router.annotations.Route;

@Route(pathClass = Lifecycle.class)
public class Activity extends AppCompatActivity {
    private ActivityLifecycleBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lifecycle);
        MyPresenter presenter = new MyPresenter();
        getLifecycle().addObserver(presenter);


    }
}
