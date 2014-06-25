package edu.radboud.ai.roboud;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.action.util.FaceExpression;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.scenario.TestScenario;
import edu.radboud.ai.roboud.senses.AndroidCamera;
import edu.radboud.ai.roboud.senses.AndroidLocation;
import edu.radboud.ai.roboud.senses.AndroidMicrophone;
import edu.radboud.ai.roboud.senses.SpeechEngine;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;
import edu.radboud.ai.roboud.util.ReadFromFile;
import edu.radboud.ai.roboud.util.WriteToFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

//import edu.radboud.ai.roboud.senses.AndroidCamera;

/**
 * Created by Pieter Marsman on 13-5-14.
 */

public class RoboudController extends Activity implements Observer, RoboMe.RoboMeListener, SensorEventListener, View.OnClickListener {
    public static final String TAG = "RoboudController";

    // Classes
    private RoboudModel model;
    private RoboudMind mind;

    // RoboMe
    private RoboMe robome;

    // UI
    private TextView textView;
    private SurfaceView surfaceView;
    private Button button;
    private ImageView imageView;

    // Senses
    private SensorManager mSensorManager;
    private HashMap<Integer, Sensor> sensors;
    private SpeechEngine speechEngine;

    //private String fileName = "hello_file";
    //private String fileLocation = "D:/Human Robot Interaction/Project/roboud/assets"; //"assets//storeinfo.txt";
    private EditText firstField;
    private EditText secondField;
    String fileDir = "";
    String fileName = "hello_file.txt";
    String FILENAME = "";

    private AndroidMicrophone mic;
    private AndroidCamera cam;
    private AndroidLocation loc;

    // Roboud
    private HashMap<Integer, ActivityResultProcessor> returnActivityDataToMap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // display the received event
            if (msg.what == 0x99)
                textView.setText((String) msg.obj);
        }
    };

    //  === START Android Activity part ===

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");

        if(fileDir == "") {
            fileDir = getFilesDir().toString();
            FILENAME = getApplicationContext().getFilesDir().getPath().toString()+ "/" + fileName; //fileDir + "/" + fileName;
        }
        Log.i(TAG, "onCreate(" + savedInstanceState + ")");

        // UI
        setContentView(R.layout.face);
        textView = (TextView) findViewById(R.id.textView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);

        try {
            Log.v(TAG,readFromFile());
        } catch (Exception e) {
            Log.v(TAG,"no saved information on phone");
        }
        Log.v(TAG,"Done reading file");

        showText("onCreate(" + savedInstanceState + ")");
        // RoboMe
        robome = new RoboMe(this.getApplicationContext(), this);

        // Variables
        returnActivityDataToMap = new HashMap<Integer, ActivityResultProcessor>();

        // Senses
        //cam = new AndroidCamera(this, surfaceView, 1000);
        loc = new AndroidLocation(this);
        mic = new AndroidMicrophone(this);
        speechEngine = new SpeechEngine(this);

        // Classes
        model = new RoboudModel(robome.isRoboMeConnected(), robome.isHeadsetPluggedIn(), robome.isListening(),
                robome.getVolume(), robome.getLibVersion());
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
        showText("onResume()");
        Log.v(TAG,"onResume");
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Scenario scenario = new TestScenario(getApplicationContext(), cam.isAvailable(), loc.isAvailable(), mic.isAvailable()
                && speechEngine.isAvailable());
        model.setScenario(scenario);

        // Variables
        // Nothing to do
    }

    protected synchronized void writeToFile(String toWrite) throws IOException {
        Log.v(TAG,"write to file");
        WriteToFile writer = new WriteToFile();
        writer.writeToFile(toWrite, FILENAME);
    }

    protected synchronized String readFromFile() throws Exception {
        Log.v(TAG,"read from file");
        ReadFromFile reader = new ReadFromFile();
        return reader.readFromFile(FILENAME);

    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause");
        try {
            writeToFile("(default output:) I am now paused, saving some text to file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        showText("onStop()");

        model.deleteObservers();
        mind.stopRunning();

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
        Log.v(TAG,"onStop");
        try {
            writeToFile("Store information now");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"Done writing to file, stopping app now");

        // UI
        // Nothing to do

        // Senses
        // Nothing to do

        // Classes
        // Nothing to do

        // Classes
        // TODO: This is an ugly try/catch
        try{
            model.deleteObservers();
            mind.stopRunning();
        } catch(Exception e)
        {
            Log.v(TAG,"Caught vague exception");
        }
        super.onStop();

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
        Log.d(TAG, incomingRobotCommand.toString());
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

    // === START Controller methods ===

    @Override
    public void update(Observable observable, Object data) {
        // Camera update
//        if (observable instanceof AndroidCamera) {
//            if (data instanceof Bitmap)
//                model.setImage((Bitmap) data);
//            if (data instanceof Camera.Face[])
//                model.setFaces(((Camera.Face[]) data).length);
//        }
        // Location update
        //else
       if (observable instanceof AndroidLocation) {
            if (data instanceof Location)
                model.setLocation((Location) data);
        }
        // Model update
        else if (observable instanceof RoboudModel) {
//            showText(model.toString());
        }
        // Unknown update
        else
            Log.d(TAG, "Unknown class observed");
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

    public void setFaceExpression(FaceExpression faceExpression) {
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
}