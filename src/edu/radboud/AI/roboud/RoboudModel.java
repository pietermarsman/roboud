package edu.radboud.ai.roboud;

import android.graphics.Bitmap;
import android.location.Location;
import com.wowwee.robome.RoboMeCommands.IncomingRobotCommand;
import com.wowwee.robome.RoboMeCommands.RobotCommand;
import edu.radboud.ai.roboud.event.Event;
import edu.radboud.ai.roboud.event.EventHistory;
import edu.radboud.ai.roboud.event.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class RoboudModel extends Observable {

    private boolean robomeConnected, robomeHeadsetPluggedIn, listening;
    private float volume;
    private String libVersion;

    private Bitmap image;
    private int faces;
    private float[] rotation, linearAcceleration, gravity, gyro, magneticField;
    private float proximity, light;
    private Location loc;

    private List<String> voiceResults;
    EventHistory events;

    private long lastModification;

    public RoboudModel(boolean robomeConnected, boolean robomeHeadsetPluggedIn, boolean listening, float volume,
                       String _libVersion) {

        this.robomeConnected = robomeConnected;
        this.robomeHeadsetPluggedIn = robomeHeadsetPluggedIn;
        this.listening = listening;
        this.volume = volume;
        this.libVersion = _libVersion;
        image = null;
        faces = 0;
        rotation = new float[3];
        linearAcceleration = new float[3];
        gravity = new float[3];
        gyro = new float[3];
        magneticField = new float[6];
        proximity = -1;
        light = -1;
        loc = null;
        voiceResults = new ArrayList<String>();
        events = new EventHistory();

        // lastModification is set by:
        changed();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RoboudModel").append("\n");
        sb.append("Connected to RoboMe: ").append(robomeConnected).append("\n");
        sb.append("Headset plugged in: ").append(robomeHeadsetPluggedIn).append("\n");
        sb.append("Listening to RoboMe: ").append(listening).append("\n");
        sb.append("Volume: ").append(volume).append("\n");
        sb.append("Library version: ").append(libVersion).append("\n");
        if (image != null)
            sb.append("Image: ").append(image.getWidth()).append("x").append(image.getHeight()).append("\n");
        else
            sb.append("Image: ").append("null").append("\n");
        sb.append("Number of faces: ").append(faces).append("\n");
        sb.append("Rotation: ").append(rotation[0]).append(" \t").append(rotation[1]).append(" \t").append(rotation[2])
                .append("\n");
        sb.append("Linear acceleration: ").append(linearAcceleration[0]).append(" \t").append(linearAcceleration[1])
                .append("\t").append(linearAcceleration[2]).append("\n");
        sb.append("Gravity: ").append(gravity[0]).append(" \t").append(gravity[1]).append(" \t").append(gravity[2])
                .append("\n");
        sb.append("Gyro: ").append(gyro[0]).append(" \t").append(gyro[1]).append(" \t").append(gyro[2]).append("\n");
        sb.append("Magnetic field: ").append(magneticField[0]).append(" \t").append(magneticField[1]).append("\t")
                .append(magneticField[2]).append("\n");
        sb.append("Proximity: ").append(proximity).append("\n");
        sb.append("Light: ").append(light).append("\n");
        if (loc != null)
            sb.append("Location: ").append(loc.getLatitude()).append(" \t").append(loc.getLongitude()).append("\n");
        else
            sb.append("Location: ").append("null").append("\n");
        sb.append("Voice result: ");
        for (String voiceResult : voiceResults)
            sb.append(voiceResult).append(", ");
        sb.append("\n");
        return sb.toString();
    }

    public void changed() {
        lastModification = System.currentTimeMillis();
        setChanged();
        notifyObservers();
    }

    public long getLastModification() {
        return lastModification;
    }

    //      === START RoboMe part ===

    public String getLibVersion() {
        return libVersion;
    }

    public void setRobomeConnected(boolean robomeConnected) {
        this.robomeConnected = robomeConnected;
        events.newEvent(new Event(EventType.ROBOME_CONNECTION, robomeConnected));
        changed();
    }

    public boolean isRobomeConnected() {
        return robomeConnected;
    }

    public boolean isRobomeHeadsetPluggedIn() {
        return robomeHeadsetPluggedIn;
    }

    public void setRobomeHeadsetPluggedIn(boolean robomeHeadsetPluggedIn) {
        this.robomeHeadsetPluggedIn = robomeHeadsetPluggedIn;
        events.newEvent(new Event(EventType.HEADPHONE_CONNECTION, robomeHeadsetPluggedIn));
        changed();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        events.newEvent(new Event(EventType.VOLUME, volume));
        changed();
    }

    public float getVolume() {
        return volume;
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
        events.newEvent(new Event(EventType.ROBOME_LISTENING, listening));
        changed();
    }

    public void receiveCommand(IncomingRobotCommand incomingRobotCommand) {
        events.newEvent(new Event(EventType.INCOMMING_COMMAND, incomingRobotCommand));
        changed();
    }

    public void sendCommand(RobotCommand outgoingCommand) {
        events.newEvent(new Event(EventType.OUTGOING_COMMAND, outgoingCommand));
        changed();
    }

    //      === START Android phone part ===


    public void setImage(Bitmap image) {
        this.image = image;
        changed();
    }

    public Bitmap getImage() {
        return image;
    }

    public float[] getRotation() {
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
        changed();
    }

    public float[] getLinearAcceleration() {
        return linearAcceleration;
    }

    public void setLinearAcceleration(float[] linearAcceleration) {
        this.linearAcceleration = linearAcceleration;
        changed();
    }

    public float[] getGravity() {
        return gravity;
    }

    public void setGravity(float[] gravity) {
        this.gravity = gravity;
        changed();
    }

    public float[] getGyro() {
        return gyro;
    }

    public void setGyro(float[] gyro) {
        this.gyro = gyro;
        changed();
    }

    public float[] getMagneticField() {
        return magneticField;
    }

    public void setMagneticField(float[] magneticField) {
        this.magneticField = magneticField;
        changed();
    }

    public float getProximity() {
        return proximity;
    }

    public void setProximity(float proximity) {
        this.proximity = proximity;
        changed();
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
        changed();
    }

    public void setLocation(Location location) {
        this.loc = location;
        changed();
    }

    public Location getLocation() {
        return loc;
    }

    public void setFaces(int faces) {
        this.faces = faces;
        changed();
    }

    public int getFaces() {
        return faces;
    }

    public void setVoiceResults(List<String> voiceResults) {
        this.voiceResults = voiceResults;
        events.newEvent(new Event(EventType.NEW_SPEECH_DATA, voiceResults));
        changed();
    }

    public List<String> getVoiceResults() {
        return voiceResults;
    }

    public List<Event> getEvents(EventType eventType, int lastN) {
        return events.getLastEventsOfType(eventType, lastN);
    }
}
