package edu.radboud.AI.roboud;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AndroidCamera implements SurfaceHolder.Callback, View.OnClickListener, View.OnTouchListener {

    //a variable to store a reference to the Image View at the main.xml file
    private ImageView iv_image;
    //a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;

    private Button button;

    //a bitmap to display the captured image
    private Bitmap bmp;

    //Camera variables
    //a surface holder
    private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;

    //sets what code should be executed after the picture is taken
    Camera.PictureCallback mCall = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            //decode the data obtained by the camera into a Bitmap
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            //set the iv_image
            iv_image.setImageBitmap(bmp);
        }
    };
    private boolean ready;

    public AndroidCamera(ImageView _imageView, SurfaceView _surfaceView, Button _button) {
        iv_image = _imageView;
        sv = _surfaceView;
        button = _button;

        button.setOnClickListener(this);
        button.setOnTouchListener(this);

        //Get a surface
        sHolder = sv.getHolder();

        //add the callback interface methods defined below as the Surface View callbacks
        sHolder.addCallback(this);
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
        mCamera = Camera.open();
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
        //stop the preview
        mCamera.stopPreview();
        //release the camera
        mCamera.release();
        //unbind the camera from this object
        mCamera = null;
    }

    @Override
    public void onClick(View v) {
        takePicture();
    }

    public void takePicture() {
        if (ready)
            mCamera.takePicture(null, null, mCall);
    }

    public void showPreview() {
        if (ready) {
            //get camera parameters
            parameters = mCamera.getParameters();

            //set camera parameters
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        showPreview();
        return false;
    }
}