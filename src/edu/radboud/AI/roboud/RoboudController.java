package edu.radboud.AI.roboud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.*;
import android.media.AudioRecord;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.view.View;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Gebruiker on 19-5-14.
 */
public class RoboudController implements RoboMe.RoboMeListener, SensorEventListener, Observer, AudioRecord.OnRecordPositionUpdateListener, View.OnClickListener {

    Handler handler;
    RoboudModel model;
    RoboMe robome;

    private AndroidCamera cam;
    private AndroidLocation loc;
    private SensorManager mSensorManager;
    private HashMap<Integer, Sensor> sensors;

    public RoboudController(Activity context, AndroidCamera _cam, Handler handler) {
        this.handler = handler;
        this.cam = _cam;

        robome = new RoboMe(context, this);
        model = new RoboudModel(robome.getLibVersion());

        loc = new AndroidLocation(context);
//        mic = new AndroidMicrophone(context, this);
        loc.addObserver(this);
        cam.addObserver(this);

        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        sensors.put(Sensor.TYPE_ROTATION_VECTOR, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensors.put(Sensor.TYPE_GRAVITY, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensors.put(Sensor.TYPE_GYROSCOPE, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensors.put(Sensor.TYPE_MAGNETIC_FIELD, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensors.put(Sensor.TYPE_PROXIMITY, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensors.put(Sensor.TYPE_LIGHT, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    public RoboudModel getModel() {
        return model;
    }

    //  === START Android Activity part ===
    //
    /**
     * Sends message to our handler to display the text in the output
     */
    public void showText(String text) {
        Message msg = new Message();
        msg.what = 0x99;
        msg.obj = text;
        handler.sendMessage(msg);
    }

    public void showImage(Bitmap image) {
        Message msg = new Message();
        msg.what = 0x98;
        msg.obj = image;
        handler.sendMessage(msg);
    }

    public void start() {
        robome.setVolume(12);
        robome.startListening();
        model.setListening(true);
        for (Sensor s : sensors.values())
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        showText("Start listening to RoboMe");
    }

    public void stop() {
        robome.stopListening();
        model.setListening(false);
        mSensorManager.unregisterListener(this);
        showText("Stop listening to RoboMe");
    }

    //  === END Android activity part ===

    //  === START RoboMe part ===

    @Override
    public void commandReceived(RoboMeCommands.IncomingRobotCommand incomingRobotCommand) {
        model.receiveCommand(incomingRobotCommand);
        showText(incomingRobotCommand.toString());
    }

    public void sendCommand(RoboMeCommands.RobotCommand outgoingCommand) {
        model.sendCommand(outgoingCommand);
        robome.sendCommand(outgoingCommand);
        showText(outgoingCommand.toString());
    }

    @Override
    public void roboMeConnected() {
        model.setRobomeConnected(true);
        showText("RoboMe connected");
    }

    @Override
    public void roboMeDisconnected() {
        model.setRobomeConnected(false);
        showText("RoboMe disconnected");
    }

    @Override
    public void headsetPluggedIn() {
        model.setRobomeHeadsetPluggedIn(true);
        showText("Headset plugged in");
    }

    @Override
    public void headsetUnplugged() {
        model.setRobomeHeadsetPluggedIn(false);
        showText("Headset unplugged");
    }

    @Override
    public void volumeChanged(float v) {
        model.setVolume(v);
        showText("Volume changed to " + v);
    }

    public boolean isSendingCommand() {
        return robome.isSendingCommand();
    }

    //  === END RoboMe part ===

    // === START Android device  part ===


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ROTATION_VECTOR: model.setRotation(event.values); break;
            case Sensor.TYPE_LINEAR_ACCELERATION: model.setLinearAcceleration(event.values); break;
            case Sensor.TYPE_GRAVITY: model.setGravity(event.values); break;
            case Sensor.TYPE_GYROSCOPE: model.setGyro(event.values); break;
            case Sensor.TYPE_MAGNETIC_FIELD: model.setMagneticField(event.values); break;
            case Sensor.TYPE_PROXIMITY: model.setProximity(event.values[0]); break;
            case Sensor.TYPE_LIGHT: model.setLight(event.values[0]); break;
            default: showText("Sensor type not recognized");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable.getClass() == AndroidCamera.class) {
            if (cam.getImage() != null)
                model.setImage(cam.getImage());
            if (cam.getFaces() != null)
                model.setFaces(cam.getFaces().length);
        }
        else if (observable.getClass() == AndroidLocation.class)
            model.setLocation(loc.getLocation());
        else
            showText("Unknown class observed");
    }

    @Override
    public void onMarkerReached(AudioRecord recorder) {
    }

    @Override
    public void onPeriodicNotification(AudioRecord recorder) {
        model.setAudioRecord(recorder);
    }

    @Override
    public void onClick(View v) {
        // Do something with button
    }

    // === END ANDROID device part ===
}
