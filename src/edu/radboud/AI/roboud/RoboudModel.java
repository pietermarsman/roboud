package edu.radboud.ai.roboud;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import com.wowwee.robome.*;
import com.wowwee.robome.RoboMeCommands.RobotCommand;
import edu.radboud.ai.roboud.action.util.FaceExpression;
import edu.radboud.ai.roboud.event.Event;
import edu.radboud.ai.roboud.event.EventHistory;
import edu.radboud.ai.roboud.event.EventType;
import edu.radboud.ai.roboud.module.util.CountNrPeopleBehaviorPhase;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.List;
import java.util.Observable;

/**
 * Created by Gebruiker on 13-5-14.
 */
public class RoboudModel extends Observable {

    public static String TAG = "RoboudModel";

    EventHistory events;
    private boolean robomeConnected, robomeHeadsetPluggedIn, listening;
    private float volume;
    private String libVersion;
    private Bitmap image;
    private int faces;
    private float[] rotation, linearAcceleration, gravity, gyro, magneticField;
    private float proximity, light;
    private Location loc;
    private long lastModification;
    private Scenario scenario;
    private FaceExpression faceExpression;
    private Integer robomeBatteryPercentage;
    private int robomeDetectionVoltage;
    private RoboMeCommands.IncomingRobotCommand robomeDirectionGameLevel;
    private GeneralStatus robomeGeneralStatus;
    private int robomeHandshakeStatus;
    private IRStatus robomeIRStatus;
    private LEDStatus robomeLEDStatus;
    private RoboMeCommands.IncomingRobotCommand robomeMoodStatus;
    private RoboMeCommands.IncomingRobotCommand robomeRemoteButton;
    private boolean distance_edge, distance_20, distance_50, distance_100, distance_far;
    private CountNrPeopleBehaviorPhase phase;

    public RoboudModel(boolean robomeConnected, boolean robomeHeadsetPluggedIn, boolean listening, float volume,
                       String _libVersion) {
        this.robomeConnected = robomeConnected;
        this.robomeHeadsetPluggedIn = robomeHeadsetPluggedIn;
        this.listening = listening;
        this.volume = volume;
        this.libVersion = _libVersion;
        this.scenario = null;
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
        events = new EventHistory();
        distance_edge = false;
        distance_20 = false;
        distance_50 = false;
        distance_100 = false;
        distance_far = false;
        phase = CountNrPeopleBehaviorPhase.GIVEASSIGNMENT;
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
        sb.append("\n");
        sb.append("Robome battery percentage: ").append(robomeBatteryPercentage).append("\n");
        sb.append("Robome detection voltage: ").append(robomeDetectionVoltage).append("\n");
        sb.append("Robome direction game level: ").append(robomeDirectionGameLevel).append("\n");
        sb.append("Robome general status: ").append(robomeGeneralStatus).append("\n");
        sb.append("Robome handshake status: ").append(robomeHandshakeStatus).append("\n");
        sb.append("Robome IR status: ").append(robomeHandshakeStatus).append("\n");
        sb.append("Robome LED status: ").append(robomeLEDStatus).append("\n");
        sb.append("Robome mood status: ").append(robomeMoodStatus).append("\n");
        sb.append("Robome remote button: ").append(robomeRemoteButton).append("\n");
        sb.append("Robome sensor status: ").append("edge: ").append(distance_edge).append(", 20: ").append(distance_20);
        sb.append(", 50: ").append(distance_50).append(", 100: ").append(distance_100).append(", far: ").append(distance_far).append("\n");
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

    public boolean isRobomeConnected() {
        return robomeConnected;
    }

    public void setRobomeConnected(boolean robomeConnected) {
        this.robomeConnected = robomeConnected;
        events.newEvent(new Event(EventType.ROBOME_CONNECTION, robomeConnected));
        changed();
    }

    public boolean isRobomeHeadsetPluggedIn() {
        return robomeHeadsetPluggedIn;
    }

    public void setRobomeHeadsetPluggedIn(boolean robomeHeadsetPluggedIn) {
        this.robomeHeadsetPluggedIn = robomeHeadsetPluggedIn;
        events.newEvent(new Event(EventType.HEADPHONE_CONNECTION, robomeHeadsetPluggedIn));
        changed();
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        events.newEvent(new Event(EventType.VOLUME, volume));
        changed();
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
        events.newEvent(new Event(EventType.ROBOME_LISTENING, listening));
        changed();
    }

    public void sendCommand(RobotCommand outgoingCommand) {
        events.newEvent(new Event(EventType.OUTGOING_COMMAND, outgoingCommand));
        changed();
    }

    public int getRobomeBatteryPercentage() {
        return robomeBatteryPercentage;
    }

    public void setRobomeBatteryPercentage(RoboMeCommands.IncomingRobotCommand robomeBatteryCommand) {
        switch (robomeBatteryCommand) {
            case kRobotIncoming_Battery10:
                robomeBatteryPercentage = 10;
                break;
            case kRobotIncoming_Battery20:
                robomeBatteryPercentage = 20;
                break;
            case kRobotIncoming_Battery40:
                robomeBatteryPercentage = 40;
                break;
            case kRobotIncoming_Battery60:
                robomeBatteryPercentage = 60;
                break;
            case kRobotIncoming_Battery80:
                robomeBatteryPercentage = 80;
                break;
            case kRobotIncoming_Battery100:
                robomeBatteryPercentage = 100;
                break;
            default:
                robomeBatteryPercentage = null;
        }
    }

    public int getRobomeDetectionVoltage() {
        return robomeDetectionVoltage;
    }

    public void setRobomeDetectionVoltage(int robomeDetectionVoltage) {
        this.robomeDetectionVoltage = robomeDetectionVoltage;
    }

    public RoboMeCommands.IncomingRobotCommand getRobomeDirectionGameLevel() {
        return robomeDirectionGameLevel;
    }

    public void setRobomeDirectionGameLevel(RoboMeCommands.IncomingRobotCommand robomeDirectionGameLevel) {
        this.robomeDirectionGameLevel = robomeDirectionGameLevel;
    }

    public GeneralStatus getRobomeGeneralStatus() {
        return robomeGeneralStatus;
    }

    public void setRobomeGeneralStatus(GeneralStatus robomeGeneralStatus) {
        // TODO use an enum
        this.robomeGeneralStatus = robomeGeneralStatus;
    }

    public int getRobomeHandshakeStatus() {
        return robomeHandshakeStatus;
    }

    public void setRobomeHandshakeStatus(int robomeHandshakeStatus) {
        this.robomeHandshakeStatus = robomeHandshakeStatus;
    }

    public IRStatus getRobomeIRStatus() {
        return robomeIRStatus;
    }

    public void setRobomeIRStatus(IRStatus robomeIRStatus) {
        this.robomeIRStatus = robomeIRStatus;
    }

    public LEDStatus getRobomeLEDStatus() {
        return robomeLEDStatus;
    }

    public void setRobomeLEDStatus(LEDStatus robomeLEDStatus) {
        this.robomeLEDStatus = robomeLEDStatus;
    }

    public RoboMeCommands.IncomingRobotCommand getRobomeMoodStatus() {
        return robomeMoodStatus;
    }

    public void setRobomeMoodStatus(RoboMeCommands.IncomingRobotCommand robomeMoodStatus) {
        // TODO read actual mood status
        this.robomeMoodStatus = robomeMoodStatus;
    }

    public RoboMeCommands.IncomingRobotCommand getRobomeRemoteButton() {
        return robomeRemoteButton;
    }

    public void setRobomeRemoteButton(RoboMeCommands.IncomingRobotCommand robomeRemoteButton) {
        // TODO what is this remoteButton actual value
        this.robomeRemoteButton = robomeRemoteButton;
    }

    public boolean isDistance_edge(){
        return distance_edge;
    }

    public boolean isDistance_20(){
        return distance_20;
    }

    public boolean isDistance_50(){
        return distance_50;
    }

    public boolean isDistance_100(){
        return distance_100;
    }

    public boolean isDistance_far(){
        return distance_far;
    }

    public CountNrPeopleBehaviorPhase getCountNrPeopleBehaviorPhase(){
        return phase;
    }

    public void setCountNrPeopleBehaviorPhase(CountNrPeopleBehaviorPhase countNrPeopleBehaviorPhase){
        phase = countNrPeopleBehaviorPhase;
    }

    public void setRobomeSensorStatus(SensorStatus robomeSensorStatus) {
        if (robomeSensorStatus.edge)
            distance_edge = true;
        else{
            distance_edge = false;
        }

        if (robomeSensorStatus.chest_20cm) {
            distance_20 = true;
        }
        else{
            distance_20 = false;
        }

        if (robomeSensorStatus.chest_50cm) {
            distance_50 = true;
        }
        else{
            distance_50 = false;
        }

        if (robomeSensorStatus.chest_100cm)
            distance_100 = true;
        else{
            distance_100 = false;
        }

        if (!distance_edge && !distance_20 && !distance_50 && !distance_100) {
            distance_far = true;
        }
        else{
            distance_far = false;
        }
        Log.i(TAG, "edge, 20, 50, 100, far = " + distance_edge + ", " + distance_20 + ", " + distance_50 + ", " + distance_100 + ", " + distance_far);
        changed();
    }

    //      === START Android phone part ===

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        changed();
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

    public Location getLocation() {
        return loc;
    }

    public void setLocation(Location location) {
        this.loc = location;
        changed();
    }

    public int getFaces() {
        return faces;
    }

    public void setFaces(int faces) {
        this.faces = faces;
        changed();
    }

    //      === START Roboud part ===

    public List<Event> getEvents(EventType eventType, int lastN) {
        return events.getLastEventsOfType(eventType, lastN);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        changed();
    }

    public FaceExpression getFaceExpression() {
        return faceExpression;
    }

    public void setFaceExpression(FaceExpression faceExpression) {
        this.faceExpression = faceExpression;
    }
}
