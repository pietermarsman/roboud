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
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.scenario.TestScenario;
import edu.radboud.ai.roboud.senses.AndroidCamera;
import edu.radboud.ai.roboud.senses.AndroidLocation;
import edu.radboud.ai.roboud.senses.AndroidMicrophone;
import edu.radboud.ai.roboud.senses.SpeechEngine;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;
import edu.radboud.ai.roboud.util.StoreInformation;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

//import edu.radboud.ai.roboud.senses.AndroidCamera;

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
    private SpeechEngine speechEngine;

    private HashMap<Integer, ActivityResultProcessor> returnActivityDataToMap;

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
        Log.i(TAG, "onCreate(" + savedInstanceState + ")");

        // RoboMe
        robome = new RoboMe(this.getApplicationContext(), this);

        // Variables
        returnActivityDataToMap = new HashMap<Integer, ActivityResultProcessor>();

        // UI
        setContentView(R.layout.main);
        logView = (TextView) findViewById(R.id.output);
        logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);

        // Senses
        cam = new AndroidCamera(this, surfaceView, 1000);
        loc = new AndroidLocation(this);
        mic = new AndroidMicrophone(this);
        speechEngine = new SpeechEngine(this);

        // Classes
        Scenario scenario = new TestScenario(this, cam.isAvailable(), loc.isAvailable(), mic.isAvailable()
                && speechEngine.isAvailable());
        model = new RoboudModel(robome.isRoboMeConnected(), robome.isHeadsetPluggedIn(), robome.isListening(),
                robome.getVolume(), robome.getLibVersion(), scenario);
        mind = new RoboudMind(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        sensors.put(Sensor.TYPE_ROTATION_VECTOR, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensors.put(Sensor.TYPE_GRAVITY, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensors.put(Sensor.TYPE_GYROSCOPE, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensors.put(Sensor.TYPE_MAGNETIC_FIELD, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensors.put(Sensor.TYPE_PROXIMITY, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensors.put(Sensor.TYPE_LIGHT, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        // UI
        Log.i(TAG, "onResume()");
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

        // Variables
        // Nothing to do
    }

    @Override
    protected void onPause() {
        // Another activity is taking focus (this activity is about to be "paused").
        Log.i(TAG, "onPause()");

        // UI
        // Nothing to do

        // Senses
        loc.deleteObservers();
        cam.deleteObservers();
        mic.deleteObservers();
        mSensorManager.unregisterListener(this);

        // Classes
        // Nothing to do


        // RoboMe
        stopListeningToRoboMe();
        model.deleteObservers();
        mind.stopRunning();

        // Variables
        // Nothing to do

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");

        Bundle bundle = new Bundle();
        bundle.putBoolean("boolean1", true);
        bundle.putString("StringName1", "InhoudString1.1");

        StoreInformation storeInformation = new StoreInformation(bundle);
//        blocks.add(storeInformation);

        // UI
        // Nothing to do

        // Senses
        // Nothing to do

        // Classes
        // Nothing to do

        // RoboMe
        // Nothing to do

        // Variables
        // Nothing to do

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // The activity is about to be destroyed.
        Log.i(TAG, "onDestroy()");

        // RoboMe
        robome = null;

        // UI
        // Nothing to do

        // Senses
        cam = null;
        loc = null;
        mic = null;

        mSensorManager = null;
        sensors = null;

        // Classes
        model = null;
        mind = null;
        speechEngine.destroy();
        speechEngine = null;

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Activity request for code " + requestCode + ", returning data to "
                + returnActivityDataToMap.get(requestCode).getClass().toString());
        returnActivityDataToMap.get(requestCode).processData(requestCode, resultCode, data);
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
        Log.d(TAG, "Start listening to RoboMe");
        if (robome != null) {
            robome.setVolume(12);
            robome.startListening();
            model.setListening(true);
        } else {
            Log.w(TAG, "Trying to start listening to RoboMe but `robome' variable is not initialized yet");
        }
    }

    public void stopListeningToRoboMe() {
        Log.d(TAG, "Stop listening to RoboMe");
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
        Log.v(TAG, "before sending command");
        model.sendCommand(outgoingCommand);
        robome.sendCommand(outgoingCommand);
        showText(outgoingCommand.toString());
    }

    @Override
    public void roboMeConnected() {
        Log.i(TAG, "RoboMe connected");
        model.setRobomeConnected(true);
        mind.startRunning();
    }

    @Override
    public void roboMeDisconnected() {
        Log.i(TAG, "RoboMe disconnected");
        model.setRobomeConnected(false);
        mind.stopRunning();
    }

    @Override
    public void headsetPluggedIn() {
        Log.i(TAG, "Headset plugged in");
        startListeningToRoboMe();
        model.setRobomeHeadsetPluggedIn(true);
    }

    @Override
    public void headsetUnplugged() {
        Log.i(TAG, "Headset unplugged");
        model.setRobomeHeadsetPluggedIn(false);
    }

    @Override
    public void volumeChanged(float v) {
        Log.v(TAG, "Volume is " + Float.toString(v) + " and i will set it to 12");
        v = 12;
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
        mic.startListening();
    }

    public void startNewActivityForResult(Intent i, int requestCode, ActivityResultProcessor returnActivityDataTo) {
        Log.d(TAG, "startNewActivityForResult, return data to " + returnActivityDataTo.getClass().toString());
        this.returnActivityDataToMap.put(requestCode, returnActivityDataTo);
        startActivityForResult(i, requestCode);
    }

    public void listenToSpeech(Observer observer) {
        mic.startListening();
        mic.addObserver(observer);
    }

    public void speakText(Observer observer, String text) {
        speechEngine.addObserver(observer);
        speechEngine.speak(text);
    }
}