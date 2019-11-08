package com.bingo.demo.read;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bingo.demo.R;
import com.bingo.demo.databinding.ActivityReadBinding;
import com.bingo.router.annotations.Route;

@Route("app/read")
public class ReadActivity extends AppCompatActivity {

    private ActivityReadBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_read);
    }
}
