package edu.radboud.ai.roboud.action;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;

import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

/**
 * @author Mike Ligthart
 */
public class LedAction extends AbstractAction {

    private final static String TAG = "LedAction";
    private LedColor color;

    public LedAction(RoboudController controller, LedColor color) {
        super(controller);
        this.color = color;
        Log.d(TAG, color.name() + " is created");
    }

    @Override
    public void doActions() {
        Log.d(TAG, "doActions() is called");
        controller.sendCommand(kRobot_ResetMood);
        controller.sendCommand(kRobot_HeartBeatOff);
        switch (color) {
            case BLUE:
                controller.sendCommand(kRobot_RGBHeartBlue);
                break;
            case CYAN:
                controller.sendCommand(kRobot_RGBHeartCyan);
                break;
            case GREEN:
                controller.sendCommand(kRobot_RGBHeartGreen);
                break;
            case ORANGE:
                controller.sendCommand(kRobot_RGBHeartOrange);
                break;
            case RED:
                controller.sendCommand(kRobot_RGBHeartRed);
                break;
            case WHITE:
                controller.sendCommand(kRobot_RGBHeartWhite);
                break;
            case YELLOW:
                controller.sendCommand(kRobot_RGBHeartYellow);
                break;
            default: //OFF
                break;
        }
        setChanged();
        Log.d(TAG, "observers are notified");
        notifyObservers();
    }
}
