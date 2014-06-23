package edu.radboud.ai.roboud;

import android.app.Activity;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.wowwee.robome.RoboMe;
import edu.radboud.ai.roboud.senses.AndroidLocation;
import edu.radboud.ai.roboud.senses.AndroidMicrophone;
import edu.radboud.ai.roboud.senses.SpeechEngine;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Observer;

//import edu.radboud.ai.roboud.senses.AndroidCamera;

/**
 * Created by Pieter Marsman on 13-5-14.
 */
public class RoboudController extends Activity implements Observer, RoboMe.RoboMeListener, SensorEventListener {

    public static final String TAG = "RoboudController";
    private AndroidMicrophone mic;
    //private AndroidCamera cam;
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
    private String fileName = "hello_file";
    private String fileLocation = "assets/storeinfo.txt";
    private EditText firstField;
    private EditText secondField;

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
        Log.v(TAG,"creating readfile");
//
//        BufferedReader buf = null;
//        try {
//            buf = new BufferedReader(new FileReader(fileLocation));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        if (buf != null) {
//            try {
//                int byteFromFile = buf.read();
//                Log.v(TAG,"Read: " + byteFromFile);
//                while(byteFromFile != -1){
//                    byteFromFile = buf.read();
//                    Log.v(TAG, "read: " + byteFromFile);
//                }
//                buf.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        // ----



            AssetManager assetManager = getAssets();
            String[] files = null;
            try {
                files = assetManager.list("image");
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }

            Log.v(TAG,Integer.toString(files.length) + " file. File name is "
                    + files[0]);
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open("readme.txt");
            } catch (IOException e) {
                Log.e("no input file found", e.getMessage());
            }

            String s = readTextFile(inputStream);
            Log.v(TAG,"Read from file: " + s);

        // ----
//        FileInputStream fos = null;
//        try {
//            fos = openFileInput(FILENAME);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }




        showText("onCreate(" + savedInstanceState + ")");
        // RoboMe
        robome = new RoboMe(this, this);

        // Variables
        returnActivityDataToMap = new HashMap<Integer, ActivityResultProcessor>();

        // UI
        setContentView(R.layout.main);
        logView = (TextView) findViewById(R.id.output);
        logScrollView = (ScrollView) findViewById(R.id.outputScrollView);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);

        // Senses
        //cam = new AndroidCamera(this, surfaceView, 1000);
        loc = new AndroidLocation(this);
        mic = new AndroidMicrophone(this);
        speechEngine = new SpeechEngine(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        sensors.put(Sensor.TYPE_ROTATION_VECTOR, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        sensors.put(Sensor.TYPE_GRAVITY, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY));
        sensors.put(Sensor.TYPE_GYROSCOPE, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        sensors.put(Sensor.TYPE_MAGNETIC_FIELD, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        sensors.put(Sensor.TYPE_PROXIMITY, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        sensors.put(Sensor.TYPE_LIGHT, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));


        // Classes
        model = new RoboudModel(robome.isRoboMeConnected(), robome.isHeadsetPluggedIn(), robome.isListening(),
                robome.getVolume(), robome.getLibVersion());
        mind = new RoboudMind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        showText("onStart()");
        checkRequirements();
    }

private String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
        while ((len = inputStream.read(buf)) != -1) {
        outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toString();
        }

    private void checkRequirements() {
        if (!mic.isAvailable())
            Log.w(TAG, "Microphone not available");
//        if (!cam.isAvailable())
//            Log.w(TAG, "Camera not available");
        if (!loc.isAvailable())
            Log.w(TAG, "Location not available");
        if (!speechEngine.isAvailable())
            Log.w(TAG, "Speech engine nog available");
        // TODO add internet
        // TODO construct scenario with this information
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
//        cam.addObserver(this);
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
        Log.v(TAG, "Pausing the app now");

        // Another activity is taking focus (this activity is about to be "paused").
        // UI
        // Nothing to do

        // Senses
        showText("onPause()");
        loc.deleteObservers();
//        cam.deleteObservers();
        mic.deleteObservers();
        mSensorManager.unregisterListener(this);

        // RoboMe
        stopListeningToRoboMe();

        // Classes
        // Nothing to do

        // Variables
        // Nothing to do
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(TAG,"Stopping the app now");
        String string = "hello world!";
        Random r = new Random();
        int intToAdd = r.nextInt();
        Log.v(TAG,"Next random int to store in memory: " + intToAdd);

//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        BufferedWriter buf = null;
        try {
            buf = new BufferedWriter(new FileWriter(fileLocation));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (buf != null) {
            try {
                buf.write(intToAdd);
                buf.write(string);
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        Bundle bundle = new Bundle();
//        bundle.putBoolean("boolean1", true);
//        bundle.putString("StringName1", "InhoudString1.1");
//        Random r = new Random();
//        int intToAdd = r.nextInt();
//        bundle.putInt("int1", intToAdd);
//        Log.v(TAG,"Next random int to store in memory: " + intToAdd);

//        StoreInformation storeInformation = new StoreInformation(bundle);
//        storeInformation.finish();
//        blocks.add(storeInformation);

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
        super.onStop();
        // Variables
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG,"Destroying the app now");


        String string = "hello world!";
        Random r = new Random();
        int intToAdd = r.nextInt();
        Log.v(TAG,"Next random int to store in memory: " + intToAdd);

//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        BufferedWriter buf = null;
        try {
            buf = new BufferedWriter(new FileWriter("assets/storeinfo.java"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (buf != null) {
            try {
                buf.write(intToAdd);
                buf.write(string);
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        super.onDestroy();
        // The activity is about to be destroyed.
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