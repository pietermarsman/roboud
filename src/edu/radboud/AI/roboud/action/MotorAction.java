package edu.radboud.ai.roboud.action;


import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.action.RobotDirection;
import edu.radboud.ai.roboud.action.RobotSpeed;
import edu.radboud.ai.roboud.action.AbstractAction;

import edu.radboud.ai.roboud.RoboudController;


import edu.radboud.ai.roboud.RoboudController;
import com.wowwee.robome.RoboMeCommands;
import edu.radboud.ai.roboud.action.RobotDirection;
import edu.radboud.ai.roboud.action.RobotSpeed;
import edu.radboud.ai.roboud.action.AbstractAction;

import edu.radboud.ai.roboud.scenario.Scenario;
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
    public void doActions(Scenario scenario, Observer abstractBehavior) {
        addObserver(abstractBehavior);
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
                switch(speed){
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
                switch(speed){
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
                switch(speed){
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
        setChanged();
        notifyObservers();
    }
}
