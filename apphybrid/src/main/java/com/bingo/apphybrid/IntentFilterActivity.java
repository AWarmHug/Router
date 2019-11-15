package com.bingo.apphybrid;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bingo.demo.approuterpath.AppHybrid;
import com.bingo.router.Router;
import com.bingo.router.annotations.Route;

@Route(pathClass = AppHybrid.Empty.class)
public class IntentFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri=getIntent().getData();
        Router.newRequest(uri).build().startBy(this);

    }
}
