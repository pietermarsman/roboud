package edu.radboud.ai.roboud.action.actions;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.LedColor;

import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

/**
 * @author Mike Ligthart
 */
public class LedAction extends AbstractAction {

    private final static String TAG = "LedAction";
    private LedColor color;

    @Deprecated
    public LedAction(RoboudController controller, LedColor color) {
        super(controller);
        this.color = color;
    }

    public LedAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions() {
        Log.d(TAG, "doActions() is called");
        controller.sendCommand(kRobot_ResetMood);
        controller.sendCommand(kRobot_HeartBeatOff);
        if(color != null) {
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
        }
        setChanged();
        notifyObservers();
    }

    public void setColor(LedColor color) {
        this.color = color;
    }
}
