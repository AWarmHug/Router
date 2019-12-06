package com.bingo.app2;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreviewView1111";
    private SurfaceHolder mHolder;
    private CameraFeature cameraFeature;
    private Camera mCamera;
    private int mCameraId;

    public CameraPreviewView(Context context) {
        this(context, null);
    }

    public CameraPreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cameraFeature = new CameraDelegate(context);
        mCamera = getFront();
        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.d(TAG, "onVisibilityChanged: ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.d(TAG, "onWindowVisibilityChanged: ");
        if (visibility == GONE || visibility == INVISIBLE) {
            cameraFeature.releaseCamera(mCamera);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, info);
            cameraFeature.setupCamera(mCamera, info, getMeasuredWidth(), getMeasuredHeight());
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraFeature.releaseCamera(mCamera);
    }


    /**
     * 拍照
     */
    public void takePhoto() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, info);
        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + System.currentTimeMillis() + ".jpg");
        cameraFeature.takePhoto(mCamera, info, file);
    }


    /**
     * 获取Camera实例
     *
     * @return Camera
     */
    private Camera getCamera(int id) {
        mCameraId = id;
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {
            Log.e(TAG, "getCamera: " + e);
        }
        return camera;
    }


    private Camera getFront() {
        return getCamera(1);
    }

    private Camera getRear() {
        return getCamera(0);
    }


}
