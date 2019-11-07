package com.warm.app2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.warm.router.annotations.Parameter;
import com.warm.router.annotations.Route;

@Route("app2/home")
public class HomeActivity extends AppCompatActivity {

    @Parameter(name = "type")
    int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
