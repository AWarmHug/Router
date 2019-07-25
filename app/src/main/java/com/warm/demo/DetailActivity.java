package com.warm.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.warm.router.annotations.Autowired;
import com.warm.router.annotations.Route;


/**
 * 作者：warm
 * 时间：2019-07-20 14:52
 * 描述：
 */
@Route("test/detail")
public class DetailActivity extends AppCompatActivity {
    @Autowired(name = "type")
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toast.makeText(this, "数据为"+getIntent().getIntExtra("type",0), Toast.LENGTH_SHORT).show();
    }
}
