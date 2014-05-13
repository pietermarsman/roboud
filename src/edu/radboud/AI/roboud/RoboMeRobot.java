package edu.radboud.AI.roboud;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.wowwee.robome.RoboMe;
import com.wowwee.robome.RoboMeCommands.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class RoboMeRobot implements RoboMe.RoboMeListener{

    private RoboMe robome;
    private List<IncomingRobotCommand> incomingCommands;
    private List<RobotCommand> outgoingCommands;

    private Bitmap frontImage;

    public RoboMeRobot(Context context) {
        robome = new RoboMe(context, this);
        commands = new LinkedList<IncomingRobotCommand>();
        robome.setDebugEnabled(true);
    }

    //      === START RoboMe part ===

    @Override
    public void commandReceived(IncomingRobotCommand incomingRobotCommand) {
        commands.add(incomingRobotCommand);
    }

    public void sendCommand(RobotCommand outgoingCommand) {
        outgoingCommands.add(outgoingCommand);
        robome.sendCommand(outgoingCommand);
    }

    @Override
    public void roboMeConnected() {
    }

    @Override
    public void roboMeDisconnected() {
    }

    @Override
    public void headsetPluggedIn() {
    }

    @Override
    public void headsetUnplugged() {
    }

    @Override
    public void volumeChanged(float v) {
    }

    public void start() {
        robome.setVolume(12);
        robome.startListening();
    }

    public void stop() {
        robome.stopListening();
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
//    public Bitmap getImageFront() {
//        int CAMERA_REQUEST = 1888;
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//            frontImage = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//        }
//    }

    //      == END Android phone part ===
}
