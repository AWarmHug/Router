package com.bingo.app2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bingo.demo.approuterpath.CameraPath;
import com.bingo.router.annotations.Route;

@Route(pathClass = CameraPath.class)
public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraPreviewView1111";
    private CameraPreviewView camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        camera=findViewById(R.id.camera);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePhoto();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

    }
}
