package com.bingo.app2;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bingo.demo.approuterpath.CameraPath;
import com.bingo.router.annotations.Route;

@Route(pathClass = CameraPath.Camera2Path.class)
public class Camera2Activity extends AppCompatActivity {
    private Camera2PreviewView camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        camera=findViewById(R.id.camera);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                camera.takePhoto();
            }
        });
    }
}
