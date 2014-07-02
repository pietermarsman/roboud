package edu.radboud.ai.roboud.action.actions;

import android.util.Log;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.HeadDirection;

/**
 * Created by Guido on 01-07-14.
 */
public class HeadAction extends AbstractAction {
    private final static String TAG = "HeadAction";
    private HeadDirection headDirection;

    public HeadAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        if (information != null) {
            if (information instanceof HeadDirection) {
                this.headDirection = (HeadDirection) information;
            } else
                throw new NullPointerException("HeadDirection cannot be null");
        }

        switch (headDirection) {
            case CENTER:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadReset);
                break;
            case UP200:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadTiltUp2);
                break;
            case DOWN200:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadTiltDown2);
                break;
            case UP500:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadTiltUp1);
                break;
            case DOWN500:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadTiltDown1);
                break;
            case ALLUP:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadTiltAllUp);
                break;
            case ALLDOWN:
                controller.sendCommand(RoboMeCommands.RobotCommand.kRobot_HeadTiltAllDown);
                break;
            default:
                throw new NullPointerException("Illegal HeadDirection");
        }
        Log.v(TAG, "after executing head direction commands");
        setChanged();
        notifyObservers();
    }

    public void setHeadDirection(HeadDirection dir) {
        this.headDirection = dir;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
