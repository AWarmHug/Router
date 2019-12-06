package com.bingo.app2;

import android.hardware.Camera;

import java.io.File;

public interface CameraFeature {

    void setupCamera(Camera camera, Camera.CameraInfo info, int width, int height);

    void releaseCamera(Camera camera);

    void takePhoto(Camera camera, Camera.CameraInfo info, File file);
}
