package com.warm.app2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.warm.router.annotations.Autowired;
import com.warm.router.annotations.Route;

@Route("app2/home")
public class HomeActivity extends AppCompatActivity {

    @Autowired(name = "type")
    int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
