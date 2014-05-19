package edu.radboud.AI.roboud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.*;
import android.os.Handler;
import android.os.Message;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands.IncomingRobotCommand;
import com.wowwee.robome.RoboMeCommands.RobotCommand;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class RoboMeRobot implements RoboMe.RoboMeListener, SensorEventListener {


    private Camera mCamera;

    private final Handler handler;

    private RoboMe robome;
    private AndroidCamera cam;

    private List<IncomingRobotCommand> incomingCommands;
    private List<RobotCommand> outgoingCommands;

    private SensorManager mSensorManager;
    private Sensor mLight;

    public RoboMeRobot(Context context, AndroidCamera _cam, Handler handler) {
        robome = new RoboMe(context, this);
        this.handler = handler;
        this.cam = _cam;

        incomingCommands = new LinkedList<IncomingRobotCommand>();
        outgoingCommands = new LinkedList<RobotCommand>();

        robome.setDebugEnabled(true);

        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_ALL);
    }

    public void start() {
        robome.setVolume(12);
        robome.startListening();
        mSensorManager.registerListener((SensorEventListener) this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        showText("Start listening to RoboMe");
    }

    public void stop() {
        robome.stopListening();
        mSensorManager.unregisterListener((SensorListener) this);
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

    public void showImage(Bitmap image) {
        Message msg = new Message();
        msg.what = 0x98;
        msg.obj = image;
        handler.sendMessage(msg);
    }

    //      === START RoboMe part ===

    @Override
    public void commandReceived(IncomingRobotCommand incomingRobotCommand) {
        incomingCommands.add(incomingRobotCommand);
        showText(incomingRobotCommand.toString());
    }

    public void sendCommand(RobotCommand outgoingCommand) {
        outgoingCommands.add(outgoingCommand);
        robome.sendCommand(outgoingCommand);
        showText(outgoingCommand.toString());
    }

    @Override
    public void roboMeConnected() {
        showText("RoboMe connected");
    }

    @Override
    public void roboMeDisconnected() {
        showText("RoboMe disconnected");
    }

    @Override
    public void headsetPluggedIn() {
        showText("Headset plugged in");
    }

    @Override
    public void headsetUnplugged() {
        showText("Headset unplugged");
    }

    @Override
    public void volumeChanged(float v) {
        showText("Volume changed to " + v);
    }

    public boolean isHeadsetPluggedIn() {
        return robome.isHeadsetPluggedIn();
    }

    public boolean isListening() {
        return robome.isListening();
    }

    public boolean isRoboMeConnected() {
        return robome.isRoboMeConnected();
    }

    public boolean isSendingCommand() {
        return robome.isSendingCommand();
    }

    public float getVolume() {
        return robome.getVolume();
    }

    public String getLibVersion() {
        return robome.getLibVersion();
    }

    //      === END RoboMe part ===

    //      === START Android phone part ===

    public Bitmap getImage() {
        return cam.getImage();
    }

    public void takePicture() {
        cam.showPreview();
        cam.takePicture();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        showText(event.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Lightsensor event

    // GPS event

    //      == END Android phone part ===
}
