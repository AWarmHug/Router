package com.bingo.app2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bingo.router.annotations.Route;

@Route(value = "app2/car", interceptors = "CarInterceptor")
public class CarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       DataBindingUtil. setContentView(this,R.layout.car_activity);

    }
}
