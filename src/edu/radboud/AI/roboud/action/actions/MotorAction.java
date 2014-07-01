package edu.radboud.ai.roboud.action.actions;


import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.RobotDirection;
import edu.radboud.ai.roboud.action.util.RobotSpeed;

import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

/**
 * @author Mike Ligthart
 */
public class MotorAction extends AbstractAction {

    private static final String TAG = "MotorAction";
    private RobotDirection dir;
    private RobotSpeed speed;


    public MotorAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        if (information != null) {
            if (information instanceof RobotDirection) {
                dir = (RobotDirection) information;
            } else if (information instanceof RobotSpeed) {
                speed = (RobotSpeed) information;
            }
        }
        if (dir == null || speed == null) {
            throw new NullPointerException("Direction and speed cannot be null");
        }
        switch (dir) {
            case FORWARD:
                switch (speed) {
                    case FASTEST:
                        controller.sendCommand(kRobot_MoveForwardSpeed1);
                        break;
                    case FAST:
                        controller.sendCommand(kRobot_MoveForwardSpeed2);
                        break;
                    case NORMAL:
                        controller.sendCommand(kRobot_MoveForwardSpeed3);
                        break;
                    case SLOW:
                        controller.sendCommand(kRobot_MoveForwardSpeed4);
                        break;
                    case SLOWEST:
                        controller.sendCommand(kRobot_MoveForwardSpeed5);
                        break;
                }
                break;
            case RIGHT:
                switch (speed) {
                    case FASTEST:
                        controller.sendCommand(kRobot_TurnRightSpeed1);
                        break;
                    case FAST:
                        controller.sendCommand(kRobot_TurnRightSpeed2);
                        break;
                    case NORMAL:
                        controller.sendCommand(kRobot_TurnRightSpeed3);
                        break;
                    case SLOW:
                        controller.sendCommand(kRobot_TurnRightSpeed4);
                        break;
                    case SLOWEST:
                        controller.sendCommand(kRobot_TurnRightSpeed5);
                        break;
                }
                break;
            case BACKWARD:
                switch (speed) {
                    case FASTEST:
                        controller.sendCommand(kRobot_MoveBackwardSpeed1);
                        break;
                    case FAST:
                        controller.sendCommand(kRobot_MoveBackwardSpeed2);
                        break;
                    case NORMAL:
                        controller.sendCommand(kRobot_MoveBackwardSpeed3);
                        break;
                    case SLOW:
                        controller.sendCommand(kRobot_MoveBackwardSpeed4);
                        break;
                    case SLOWEST:
                        controller.sendCommand(kRobot_MoveBackwardSpeed5);
                        break;
                }
                break;
            case LEFT:
                switch (speed) {
                    case FASTEST:
                        controller.sendCommand(kRobot_TurnLeftSpeed1);
                        break;
                    case FAST:
                        controller.sendCommand(kRobot_TurnLeftSpeed2);
                        break;
                    case NORMAL:
                        controller.sendCommand(kRobot_TurnLeftSpeed3);
                        break;
                    case SLOW:
                        controller.sendCommand(kRobot_TurnLeftSpeed4);
                        break;
                    case SLOWEST:
                        controller.sendCommand(kRobot_TurnLeftSpeed5);
                        break;
                }
                break;
        }
        Log.v(TAG, "after executing motor commands");
        setChanged();
        notifyObservers();
    }

    @Override
    public Object getInformation() {
        return null;
    }

    public void setDirAndSpeed(RobotDirection dir, RobotSpeed speed) {
        this.dir = dir;
        this.speed = speed;
    }
}
