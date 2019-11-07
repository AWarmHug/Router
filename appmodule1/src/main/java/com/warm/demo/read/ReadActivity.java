package com.warm.demo.read;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.warm.demo.R;
import com.warm.demo.databinding.ActivityReadBinding;
import com.warm.router.annotations.Route;

@Route("app/read")
public class ReadActivity extends AppCompatActivity {

    private ActivityReadBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_read);
    }
}
