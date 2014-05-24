package edu.radboud.ai.roboud.senses;

import java.io.IOException;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidCamera extends Observable implements SurfaceHolder.Callback {

    //a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;
    //a surface holder
    private SurfaceHolder sHolder;

    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;

    private int refreshRate;
    private Timer timer;

    //sets what code should be executed after the picture is taken
    Camera.PictureCallback mCall = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            //decode the data obtained by the camera into a Bitmap
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            setChanged();
            notifyObservers(bmp);
        }
    };

    Camera.FaceDetectionListener faceDetectionListener = new Camera.FaceDetectionListener() {
        @Override
        public void onFaceDetection(Camera.Face[] _faces, Camera camera) {
            Camera.Face[] faces = _faces;
            setChanged();
            notifyObservers(faces);
        }
    };

    private boolean ready;

    public AndroidCamera(SurfaceView _surfaceView, int _refreshRate) {
        this.refreshRate = _refreshRate;
        this.sv = _surfaceView;

        //Get a surface
        sHolder = sv.getHolder();

        //add the callback interface methods defined below as the Surface View callbacks
        sHolder.addCallback(this);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                takePicture();
            }
        }, refreshRate, refreshRate);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ready = true;
        // The Surface has been created, acquire the camera and tell it where
        // to draw the preview.
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mCamera.setFaceDetectionListener(faceDetectionListener);
        try {
            mCamera.setPreviewDisplay(sHolder);
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        ready = false;
        //stopListeners the preview
        mCamera.stopPreview();
        mCamera.stopFaceDetection();
        //release the camera
        mCamera.release();
        //unbind the camera from this object
        mCamera = null;
    }

    public void takePicture() {
        if (ready) {
            //get camera parameters
            parameters = mCamera.getParameters();

            //set camera parameters
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            mCamera.startFaceDetection();
            mCamera.takePicture(null, null, mCall);
        }
    }
}