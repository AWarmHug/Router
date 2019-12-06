package com.bingo.app2;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

public class CameraPreviewView2 extends TextureView implements TextureView.SurfaceTextureListener {


    public CameraPreviewView2(Context context) {
        this(context,null);
    }

    public CameraPreviewView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraPreviewView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
