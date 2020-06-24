package com.bingo.app2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2PreviewView extends TextureView implements TextureView.SurfaceTextureListener {
    private static final String TAG = "CameraPreviewView2";
    Context mContext;
    CameraManager cameraManager;
    CameraCharacteristics characteristics;
    private String mCameraId;
    private Size[] sizes;
    private Size mPreviewSize;
    private boolean mFaceDetectSupported;
    private Integer mFaceDetectMode;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewRequestBuilder;
    Handler mBackgroundHandler;
    public Camera2PreviewView(Context context) {
        this(context, null);
    }

    public Camera2PreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Camera2PreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        setSurfaceTextureListener(this);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        setupCamera();
        openCamera(surface);
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

    private void setupCamera() {
        cameraManager = (CameraManager)mContext.getSystemService(Context.CAMERA_SERVICE);
        try{
            for (String id : cameraManager.getCameraIdList()) {

                //获取代表摄像头特征类characteristics
                characteristics = cameraManager.getCameraCharacteristics(id);

                //如果是前置摄像头
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                    mCameraId = id ;

                    StreamConfigurationMap streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                    sizes = streamConfigurationMap.getOutputSizes(SurfaceHolder.class);

                    //设置预览大小
                    mPreviewSize = sizes[0];

                    //获取人脸检测参数
                    int[] FD =characteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
                    int maxFD=characteristics.get(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT);

                    if (FD.length>0) {
                        List<Integer> fdList = new ArrayList<>();
                        for (int FaceD : FD
                        ) {
                            fdList.add(FaceD);
                            Log.e(TAG, "setUpCameraOutputs: FD type:" + Integer.toString(FaceD));
                        }
                        Log.e(TAG, "setUpCameraOutputs: FD count" + Integer.toString(maxFD));

                        if (maxFD > 0) {
                            mFaceDetectSupported = true;
                            mFaceDetectMode = Collections.max(fdList);
                        }
                    }
                }
            }
        } catch ( CameraAccessException e ){
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public void openCamera(SurfaceTexture surface){
        HandlerThread handlerThread=new HandlerThread("1111");
        handlerThread.start();
        mBackgroundHandler =new Handler(handlerThread.getLooper());
        try {
            cameraManager.openCamera(mCameraId, new CameraDevice.StateCallback(){

                //若摄像机打开成功则回调此方法
                @Override
                public void onOpened(CameraDevice camera) {
                    //获取cameraDevice
                    cameraDevice = camera;
                    //开启预览
                    startPreview(surface);
                }

                //摄像机连接断开回调此方法
                @Override
                public void onDisconnected(CameraDevice camera) {

                    if(cameraDevice != null ){
                        cameraDevice.close();
                    }
                }
                //出现异常回调此方法
                @Override
                public void onError(CameraDevice camera, int error) {
                    if(cameraDevice != null ){
                        cameraDevice.close();
                    }
                }

            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    public void startPreview(SurfaceTexture surfaceTexture){
        try{
            Surface surface = new Surface(surfaceTexture);
            previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewRequestBuilder.addTarget(surface);
            /*previewRequestBuilder.addTarget(mImageReader.getSurface());*/
            previewRequestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE,
                    CameraMetadata.STATISTICS_FACE_DETECT_MODE_FULL);
            ImageReader mImageReader = ImageReader.newInstance(getMeasuredWidth(), getMeasuredHeight(),
                    ImageFormat.JPEG, 1);
            cameraDevice.createCaptureSession(Arrays.asList(surface,mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        //构建captureRequest对象
                        CaptureRequest captureRequest = previewRequestBuilder.build();
                        //设置人脸检测
                        previewRequestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE,mFaceDetectMode);
                        CameraCaptureSession captureSession = session;
                        captureSession.setRepeatingRequest(captureRequest, new CameraCaptureSession.CaptureCallback() {

                            /**
                             * 对摄像头返回的结果进行处理,并获取人脸数据
                             * @param result 摄像头数据
                             */
                            private void process(CaptureResult result) {

                                //获得Face类
                                Face face[]=result.get(CaptureResult.STATISTICS_FACES);

                                //如果有人脸的话
                                if (face!=null&&face.length>0 ){
                                    Log.e(TAG, "face detected " + Integer.toString(face.length));

                                    //获取人脸矩形框
                                    Rect bounds = face[0].getBounds();

                                    float y = mPreviewSize.getHeight()/2 - bounds.top ;

                                    Log.e("height" , String.valueOf(mPreviewSize.getWidth()));
                                    Log.e("top" , String.valueOf(y));
                                    Log.e("left" ,  String.valueOf(bounds.left));
                                    Log.e("right" , String.valueOf(bounds.right));


                                }


                            }

                            @Override
                            public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
                                super.onCaptureStarted(session, request, timestamp, frameNumber);
                            }

                            @Override
                            public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                                process(partialResult);
                            }

                            @Override
                            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                                process(result);
                            }
                        },mBackgroundHandler);


                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }


            }, null);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }

    }

}
