package edu.radboud.ai.roboud.action;

import com.wowwee.robome.RoboMeCommands.*;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */

public enum LedColor {BLUE, CYAN, GREEN, ORANGE, RED, WHITE, YELLOW};

public class LedAction extends edu.radboud.AI.roboud.action.AbstractAction {

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour, LedColor color) {
        addObserver(abstractBehaviour);
        RobotCommand.kRobot_ShowMoodOff;
        RobotCommand.kRobot_HeartBeatOff;
        switch(color){

            case BLUE:
                break;
            case CYAN:
                break;
            case GREEN:
                break;
            case ORANGE:
                break;
            case RED:
                break;
            case WHITE:
                break;
            case YELLOW:
                break;
        }
        setChanged();
        notifyObservers();
    }
}
