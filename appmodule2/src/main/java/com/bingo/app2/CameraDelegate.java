package com.bingo.app2;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.view.Surface;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okio.Okio;

public  class CameraDelegate implements CameraFeature {


    private Context mContext;

    public CameraDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setupCamera(Camera camera, Camera.CameraInfo info, int width, int height) {
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        Camera.Size previewSize = getOptimalSize(parameters.getSupportedPreviewSizes(), width, height);
        parameters.setPreviewSize(previewSize.width, previewSize.height);

        Camera.Size pictureSize = getOptimalSize(parameters.getSupportedPictureSizes(), width, height);
        parameters.setPictureSize(pictureSize.width, pictureSize.height);
        camera.setParameters(parameters);
        setCameraDisplayOrientation(camera,info);
    }


    @Override
    public void releaseCamera(Camera camera) {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
        }
    }


    @Override
    public void takePhoto(Camera camera, Camera.CameraInfo info,File file) {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Okio.buffer(Okio.sink(file)).write(data);
                    setPictureDegree(file.getPath(), info.orientation);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static Camera.Size getOptimalSize(@NonNull List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }


    public void setCameraDisplayOrientation(Camera camera,Camera.CameraInfo info) {
        int rotation = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    /**
     * 读取图片旋转的角度
     *
     * @param filename
     * @return
     */
    public static void setPictureDegree(String filename, int degree) {
        try {
            if (degree == 0) {
                return;
            }
            int rotate = ExifInterface.ORIENTATION_UNDEFINED;
            switch (degree) {
                case 90:
                    rotate = ExifInterface.ORIENTATION_ROTATE_90;
                    break;
                case 180:
                    rotate = ExifInterface.ORIENTATION_ROTATE_180;
                    break;
                case 270:
                    rotate = ExifInterface.ORIENTATION_ROTATE_270;
                    break;
                default:
                    break;
            }

            ExifInterface exifInterface = new ExifInterface(filename);
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,
                    String.valueOf(rotate));
            exifInterface.saveAttributes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
