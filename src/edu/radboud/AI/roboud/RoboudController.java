package edu.radboud.ai.roboud;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.*;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.action.util.FaceExpression;
import edu.radboud.ai.roboud.senses.AndroidCamera;
import edu.radboud.ai.roboud.senses.AndroidLocation;
import edu.radboud.ai.roboud.senses.AndroidMicrophone;
import edu.radboud.ai.roboud.senses.SpeechEngine;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;
import edu.radboud.ai.roboud.util.Scenario;
import edu.radboud.ai.roboud.util.io.DataWriter;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

//import edu.radboud.ai.roboud.senses.AndroidCamera;

/**
 * Created by Pieter Marsman on 13-5-14.
 */

public class RoboudController extends Activity implements Observer, RoboMe.RoboMeListener, SensorEventListener, View.OnClickListener {

    public static final String TAG = "RoboudController";
    public static final String FILENAME = "hello_world.xml";
    // Varialbes
    File file;
    DataWriter dataWriter;
    // Classes
    private RoboudModel model;
    private RoboudMind mind;
    // RoboMe
    private RoboMe robome;
    // UI
    private TextView textView;
    private String text;
    private SurfaceView surfaceView;
    private ImageView imageView;
    // Senses
    private SensorManager mSensorManager;
    private HashMap<Integer, Sensor> sensors;
    private SpeechEngine speechEngine;
    private EditText firstField;
    private EditText secondField;
    private AndroidMicrophone mic;
    private AndroidCamera cam;
    private AndroidLocation loc;
    // Roboud
    private HashMap<Integer, ActivityResultProcessor> returnActivityDataToMap;
    private Handler textViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // display the received event
            if (msg.what == 0x99)
                textView.setText((String) msg.obj);
        }
    };

    private Handler faceExpressionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // show the face
            if (msg.what == 0x98)
                setFaceExpression((FaceExpression) msg.obj);
        }
    };

    //  === START Android Activity part ===

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(" + savedInstanceState + ")");

        // UI
        setContentView(R.layout.face_nothing);
        textView = (TextView) findViewById(R.id.textView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        imageView = (ImageView) findViewById(R.id.imageView);

        // RoboMe
        robome = new RoboMe(this.getApplicationContext(), this);
        robome.setDebugEnabled(true);

        // Variables
        returnActivityDataToMap = new HashMap<Integer, ActivityResultProcessor>();

        // Senses
        cam = new AndroidCamera(this, surfaceView, 1000);
        loc = new AndroidLocation(this);
        mic = AndroidMicrophone.getInstance(this);
        speechEngine = new SpeechEngine(this);

        // Classes
        model = new RoboudModel(false, robome.isHeadsetPluggedIn(), robome.isListening(),
                robome.getVolume(), robome.getLibVersion());
        Log.i(TAG, model.toString());

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        sensors.put(Sensor.TYPE_ROTATION_VECTOR, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensors.put(Sensor.TYPE_GRAVITY, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensors.put(Sensor.TYPE_GYROSCOPE, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensors.put(Sensor.TYPE_MAGNETIC_FIELD, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensors.put(Sensor.TYPE_PROXIMITY, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensors.put(Sensor.TYPE_LIGHT, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

        // Read data from previous sesion. Can only be done after model is initialized.
        dataWriter = new DataWriter();
        readFromFile();
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
        Log.i(TAG, "onResume");

        // UI;
        if (text != null)
            showText(text);

        // Senses
        loc.addObserver(this);
        cam.addObserver(this);
        for (Sensor s : sensors.values())
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

        // Classes
        Log.d(TAG, "speechEngine is Available: " + speechEngine.isAvailable());

        // TODO Apparently the speechEngine is not available although it works...
        Scenario scenario = new Scenario(getApplicationContext(), false, true, mic.isAvailable(), cam.isAvailable(), loc.isAvailable());
        model.setScenario(scenario);
        model.addObserver(this);
        mind = RoboudMind.getInstance(this, scenario);

        // RoboMe
        startListeningToRoboMe();
        startMindThread();
    }

    @Override
    protected void onPause() {
        // Another activity is taking focus (this activity is about to be "paused").
        Log.i(TAG, "onPause");

        // UI
        // Nothing to do

        // Senses
        loc.deleteObservers();
        cam.deleteObservers();
        // Not delete observer from mic because it can be used for an activity
        mSensorManager.unregisterListener(this);

        // Classes
        // Nothing to do

        // RoboMe
        stopListeningToRoboMe();
        model.deleteObserver(this);
        stopMindThread();

        // Variables
        // Nothing to do
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");

        // Write the essential parts of the model to a file. This should be done before the model is destroyed.
        writeToFile();

        // UI
        // Nothing to do

        // Senses
        // Nothing to do

        // Classes
        // Nothing to do

        // Classes
        // TODO: This is an ugly try/catch
        model.deleteObserver(this);

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
     * Sends message to our textViewHandler to display the text in the output
     */
    public void showText(String text) {
        this.text = text;
        Message msg = new Message();
        msg.what = 0x99;
        msg.obj = text;
        textViewHandler.sendMessage(msg);
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
        Log.i(TAG, "Command received " + incomingRobotCommand.toString());
        if (incomingRobotCommand.isBatteryStatus())
            model.setRobomeBatteryPercentage(incomingRobotCommand);
        else if (incomingRobotCommand.isDetectionVoltage())
            model.setRobomeDetectionVoltage(incomingRobotCommand.readDetectionVoltage());
        else if (incomingRobotCommand.isDirectionGameLevel())
            model.setRobomeDirectionGameLevel(incomingRobotCommand);
        else if (incomingRobotCommand.isGeneralStatus())
            model.setRobomeGeneralStatus(incomingRobotCommand.readGeneralStatus());
        else if (incomingRobotCommand.isHandshakeStatus())
            model.setRobomeHandshakeStatus(incomingRobotCommand.readHandshakeStatus());
        else if (incomingRobotCommand.isIRStatus())
            model.setRobomeIRStatus(incomingRobotCommand.readIRStatus());
        else if (incomingRobotCommand.isLEDStatus())
            model.setRobomeLEDStatus(incomingRobotCommand.readLEDStatus());
        else if (incomingRobotCommand.isMoodValue())
            model.setRobomeMoodStatus(incomingRobotCommand);
        else if (incomingRobotCommand.isRemoteButton())
            model.setRobomeRemoteButton(incomingRobotCommand);
        else if (incomingRobotCommand.isSensorStatus())
            model.setRobomeSensorStatus(incomingRobotCommand.readSensorStatus());
        else
            Log.w(TAG, "IncomingRobotCommand received but it was of unkown type");
    }


    public void sendCommand(RoboMeCommands.RobotCommand outgoingCommand) {
        Log.v(TAG, "before sending command");
        model.sendCommand(outgoingCommand);
        robome.sendCommand(outgoingCommand);
        Log.d(TAG, outgoingCommand.toString());
    }

    @Override
    public void roboMeConnected() {
        Log.i(TAG, "RoboMe connected");
        model.setRobomeConnected(true);
    }

    @Override
    public void roboMeDisconnected() {
        Log.i(TAG, "RoboMe disconnected");
        model.setRobomeConnected(false);
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
        stopListeningToRoboMe();
    }

    @Override
    public void volumeChanged(float v) {
        model.setVolume(v);
        Log.d(TAG, "Volume changed to " + v);
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
                Log.d(TAG, "Sensor type not recognized");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected synchronized void writeToFile() {
        Log.v(TAG, "write to file");
        file = new File(getFilesDir(), FILENAME);
        Log.d(TAG, "Filename: " + file.toString());
        dataWriter.writeToFile(file, model);
    }

    protected synchronized void readFromFile() {
        Log.v(TAG, "read from file");
        file = new File(getFilesDir(), FILENAME);
        Log.d(TAG, "Filename: " + file.toString());
        dataWriter.readFromFile(file, model);
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
            Log.v(TAG, model.toString());
        }
        // Unknown update
        else
            Log.w(TAG, "Unknown class observed: " + observable.getClass());
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
        mic.addObserver(observer);
        mic.startListening();
    }

    public void speakText(Observer observer, String text) {
        speechEngine.addObserver(observer);
        speechEngine.speak(text);
    }

    public void displayFaceExpression(FaceExpression faceExpression) {
        Message m = new Message();
        m.what = 0x98;
        m.obj = faceExpression;
        faceExpressionHandler.sendMessage(m);
    }

    private void setFaceExpression(FaceExpression faceExpression) {
        Log.i(TAG, "setFaceExpression: " + faceExpression);
        Drawable drawable;
        switch (faceExpression) {
            case SAD:
                drawable = getResources().getDrawable(R.drawable.face_sad);
                break;
            case SMILE_BIG:
                drawable = getResources().getDrawable(R.drawable.face_smile_big);
                break;
            case SMILE_NORMAL:
                drawable = getResources().getDrawable(R.drawable.face_smile_normal);
                break;
            case SMILE_SMALL:
                drawable = getResources().getDrawable(R.drawable.face_smile_small);
                break;
            case SURPRISED:
                drawable = getResources().getDrawable(R.drawable.face_supprised);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.face_smile_normal);
        }
        imageView.setImageDrawable(drawable);
        showText(faceExpression.toString());
        model.setFaceExpression(faceExpression);
    }

    public RoboudModel getModel() {
        return model;
    }

    private void startMindThread() {
        if (!mind.isRunning()) {
            mind.startRunning();
        }
    }

    private void stopMindThread() {
        //mind.stopRunning();
    }

    public void appInDisconnectedMode() {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.face_nothing));
        textView.setText("Please connect the app to the robot body...");
    }

    public void appInConnectedMode() {
        //TODO this should resume at the previous settings before it was disconnected
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.face_smile_normal));
        textView.setText("Status");
    }

}