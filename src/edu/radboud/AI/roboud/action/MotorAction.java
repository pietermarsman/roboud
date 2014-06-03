package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.action.RobotDirection;
import edu.radboud.ai.roboud.action.RobotSpeed;
import edu.radboud.ai.roboud.action.AbstractAction;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.RoboudController;
import java.util.Observer;

import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class MotorAction extends AbstractAction {

    public MotorAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void executeAction(Scenario scenario) {
        // TODO
        actionDone();
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour, RoboudController roboudController) {
        addObserver(abstractBehaviour);
        //Some how get direction and speed
        RobotDirection dir = RobotDirection.FORWARD;
        RobotSpeed speed = RobotSpeed.NORMAL;

        //Not finished
        switch(dir){
            case FORWARD:
                switch(speed){
                    case FASTEST:
                        roboudController.sendCommand(kRobot_MoveForwardSpeed1);
                        break;
                    case FAST:
                        roboudController.sendCommand(kRobot_MoveForwardSpeed2);
                        break;
                    case NORMAL:
                        roboudController.sendCommand(kRobot_MoveForwardSpeed3);
                        break;
                    case SLOW:
                        roboudController.sendCommand(kRobot_MoveForwardSpeed4);
                        break;
                    case SLOWEST:
                        roboudController.sendCommand(kRobot_MoveForwardSpeed5);
                        break;
                }
                break;
            case RIGHT:
                break;
            case BACKWARD:
                break;
            case LEFT:
                break;
        }
        setChanged();
        notifyObservers();
    }
}
