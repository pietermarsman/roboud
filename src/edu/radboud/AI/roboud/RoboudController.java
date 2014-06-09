package edu.radboud.ai.roboud;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.*;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.senses.AndroidCamera;
import edu.radboud.ai.roboud.senses.AndroidLocation;
import edu.radboud.ai.roboud.senses.AndroidMicrophone;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 13-5-14.
 */
public class RoboudController extends Activity implements Observer, RoboMe.RoboMeListener, SensorEventListener, View.OnClickListener {

    public static final String TAG = "RoboudController";
    private AndroidMicrophone mic;
    private AndroidCamera cam;
    private AndroidLocation loc;
    private RoboudModel model;
    private RoboudMind mind;
    private RoboMe robome;
    private TextView logView;
    private ScrollView logScrollView;
    private SurfaceView surfaceView;
    private Button button;
    private SensorManager mSensorManager;
    private HashMap<Integer, Sensor> sensors;

    private ActivityResultProcessor returnActivityDataTo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // display the received event
            if (msg.what == 0x99)
                logView.setText(logView.getText() + "\n" + (String) msg.obj);
            logScrollView.smoothScrollTo(0, logView.getHeight());
        }
    };


    public RoboudModel getModel() {
        return model;
    }

    //  === START Android Activity part ===


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showText("onCreate(" + savedInstanceState + ")");

        // UI
        setContentView(R.layout.main);
        logView = (TextView) findViewById(R.id.output);
        logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);

        // Senses
        cam = new AndroidCamera(surfaceView, 1000);
        loc = new AndroidLocation(this);
        mic = new AndroidMicrophone(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        sensors.put(Sensor.TYPE_ROTATION_VECTOR, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensors.put(Sensor.TYPE_GRAVITY, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensors.put(Sensor.TYPE_GYROSCOPE, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensors.put(Sensor.TYPE_MAGNETIC_FIELD, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensors.put(Sensor.TYPE_PROXIMITY, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensors.put(Sensor.TYPE_LIGHT, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

        // RoboMe
        robome = new RoboMe(this, this);

        // Classes
        model = new RoboudModel(robome.isRoboMeConnected(), robome.isHeadsetPluggedIn(), robome.isListening(),
                robome.getVolume(), robome.getLibVersion());
        mind = new RoboudMind(this);

        // Variables
        returnActivityDataTo = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        showText("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        // UI
        showText("onResume()");
        button.setOnClickListener(this);

        // Senses
        loc.addObserver(this);
        cam.addObserver(this);
        mic.addObserver(this);
        for (Sensor s : sensors.values())
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

        // RoboMe
        startListeningToRoboMe();

        // Classes
        model.addObserver(this);
        mind.startRunning();

        // Variables
        // Nothing to do
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        // UI
        // Nothing to do

        // Senses
        showText("onPause()");
        loc.deleteObservers();
        cam.deleteObservers();
        mic.deleteObservers();
        mSensorManager.unregisterListener(this);

        // RoboMe
        stopListeningToRoboMe();

        // Classes
        // Nothing to do

        // Variables
        // Nothing to do
    }

    @Override
    public void onStop() {
        super.onStop();
        // UI
        // Nothing to do

        // Senses
        // Nothing to do

        // RoboMe
        // Nothing to do

        // Classes
        showText("onStop()");
        model.deleteObservers();
        mind.stopRunning();

        // Variables
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "Activity result for code " + requestCode);
        returnActivityDataTo.processData(requestCode, resultCode, data);
        returnActivityDataTo = null;
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

    public void startListeningToRoboMe() {
        if (robome != null) {
            robome.setVolume(12);
            robome.startListening();
            model.setListening(true);
        } else {
            Log.w(TAG, "Trying to start listening to RoboMe but `robome' variable is not initialized yet");
        }
    }

    public void stopListeningToRoboMe() {
        if (robome != null) {
            robome.stopListening();
            model.setListening(false);
        } else {
            Log.w(TAG, "Trying to stop listening to RoboMe but `robome' variable is not initialized yet");
        }
    }

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
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ROTATION_VECTOR:
                model.setRotation(event.values);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                model.setLinearAcceleration(event.values);
                break;
            case Sensor.TYPE_GRAVITY:
                model.setGravity(event.values);
                break;
            case Sensor.TYPE_GYROSCOPE:
                model.setGyro(event.values);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                model.setMagneticField(event.values);
                break;
            case Sensor.TYPE_PROXIMITY:
                model.setProximity(event.values[0]);
                break;
            case Sensor.TYPE_LIGHT:
                model.setLight(event.values[0]);
                break;
            default:
                showText("Sensor type not recognized");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // === START Controller methods ===

    @Override
    public void update(Observable observable, Object data) {
        // Camera update
        if (observable instanceof AndroidCamera) {
            if (data instanceof Bitmap)
                model.setImage((Bitmap) data);
            if (data instanceof Camera.Face[])
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
//            showText(model.toString());
        }
        // Unknown update
        else
            showText("Unknown class observed");
    }

    @Override
    public void onClick(View v) {
        mic.startListening(this);
    }

    public void startNewActivityForResult(Intent i, int requestCode, ActivityResultProcessor returnActivityDataTo) {
        this.returnActivityDataTo = returnActivityDataTo;
        startActivityForResult(i, requestCode);
    }

    public void listenToSpeech(Observer observer) {
        mic.startListening(observer);
    }
}