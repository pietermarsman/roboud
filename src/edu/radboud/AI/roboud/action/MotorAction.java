package edu.radboud.ai.roboud.action;

import com.wowwee.robome.RoboMeCommands;
import edu.radboud.AI.roboud.action.RobotDirection;
import edu.radboud.AI.roboud.action.RobotSpeed;
import edu.radboud.ai.roboud.action.AbstractAction;

import edu.radboud.ai.roboud.RoboudController;

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
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        addObserver(abstractBehaviour);
        //Some how get direction and speed
        RobotDirection dir = RobotDirection.FORWARD;
        RobotSpeed speed = RobotSpeed.NORMAL;

        //Not finished
        switch(dir){
            case FORWARD:
                switch(speed){
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
