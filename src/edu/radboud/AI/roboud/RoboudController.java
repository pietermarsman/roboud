package edu.radboud.ai.roboud;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.*;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.event.EventHistory;
import edu.radboud.ai.roboud.senses.AndroidCamera;
import edu.radboud.ai.roboud.senses.AndroidLocation;
import edu.radboud.ai.roboud.senses.AndroidMicrophone;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 13-5-14.
 */
public class RoboudController extends Activity implements Observer, RoboMe.RoboMeListener, SensorEventListener, View.OnClickListener {

    private TextView logView;
    private ScrollView logScrollView;
    private SurfaceView surfaceView;
    private Button button;

    AndroidMicrophone mic;
    RoboudModel model;
    RoboudMind mind;
    EventHistory events;
    RoboMe robome;

    private SensorManager mSensorManager;
    private HashMap<Integer, Sensor> sensors;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // display the received event
            if (msg.what == 0x99 )
                logView.setText((String) msg.obj);
            logScrollView.smoothScrollTo(0, logView.getHeight());
        }
    };

    public void startListeningToRoboMe() {
        robome.setVolume(12);
        robome.startListening();
        model.setListening(true);
    }

    public void stopListeningToRoboMe() {
        robome.stopListening();
        model.setListening(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Send speech data to the controller
        if (requestCode == AndroidMicrophone.REQUEST_CODE) {
            mic.processData(requestCode, resultCode, data);
        }
    }


    public RoboudModel getModel() {
        return model;
    }

    //  === START Android Activity part ===


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        logView = (TextView) findViewById(R.id.output);
        logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);

        robome = new RoboMe(this, this);

        AndroidCamera cam = new AndroidCamera(surfaceView, 1000);
        AndroidLocation loc = new AndroidLocation(this);
        mic = new AndroidMicrophone(this);
        loc.addObserver(this);
        cam.addObserver(this);
        mic.addObserver(this);

        model = new RoboudModel(robome.getLibVersion(), loc, cam, mic);
        events = new EventHistory();
        mind = new RoboudMind(this);
        new Thread(mind).start();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        sensors.put(Sensor.TYPE_ROTATION_VECTOR, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensors.put(Sensor.TYPE_GRAVITY, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensors.put(Sensor.TYPE_GYROSCOPE, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensors.put(Sensor.TYPE_MAGNETIC_FIELD, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensors.put(Sensor.TYPE_PROXIMITY, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensors.put(Sensor.TYPE_LIGHT, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

        button.setOnClickListener(this);
        model.addObserver(this);
    }

    /** Start listening to events from the gun when the app starts or resumes from background */
    @Override
    public void onResume(){
        super.onResume();
        startListeningToRoboMe();
        for (Sensor s : sensors.values())
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        showText("Start listening to RoboMe");
    }

    @Override
    public void onStop(){
        super.onStop();
        stopListeningToRoboMe();
        mSensorManager.unregisterListener(this);
        showText("Stop listening to RoboMe");
    }

    /**
     * Sends message to our handler to display the text in the output
     */
    public void showText(String text) {
        Message msg = new Message();
        msg.what = 0x99;
        msg.obj = text;
        handler.sendMessage(msg);
    }

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
        // Camera update
        if (observable instanceof AndroidCamera) {
            if (data instanceof Bitmap)
                model.setImage((Bitmap) data);
            if(data instanceof Camera.Face[])
                model.setFaces(((Camera.Face[]) data).length);
        }
        // Location update
        else if (observable instanceof AndroidLocation) {
            if (data instanceof Location)
                model.setLocation((Location) data);
        }
        // Microphone update
        else if (observable instanceof AndroidMicrophone) {
            if (data instanceof List)
                model.setVoiceResults((List<String>) data);
        }
        // Model update
        else if (observable instanceof RoboudModel) {
            showText(model.toString());
        }
        // Unknown update
        else
            showText("Unknown class observed");
    }

    @Override
    public void onClick(View v) {
        listenToSpeech(this);
    }

    public void listenToSpeech(Observer observer) {
        mic.startListening(observer);
    }

    // === END ANDROID device part ===
}