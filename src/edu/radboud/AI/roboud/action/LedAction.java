package edu.radboud.ai.roboud.action;

import com.wowwee.robome.RoboMeCommands.*;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */

public enum class LedColor {};

public class LedAction extends edu.radboud.AI.roboud.action.AbstractAction {

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        addObserver(abstractBehaviour);
        RobotCommand.kRobot_ShowMoodOff;
        RobotCommand.kRobot_HeartBeatOff;
        RobotCommand.kRobot_RGBHeartBlue;
        setChanged();
        notifyObservers();
    }
}
