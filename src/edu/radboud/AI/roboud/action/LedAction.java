package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import com.wowwee.robome.RoboMeCommands.RobotCommand;
import edu.radboud.ai.roboud.action.LedColor;
import edu.radboud.ai.roboud.RoboudController;
import com.wowwee.robome.RoboMeCommands.RobotCommand;
import edu.radboud.ai.roboud.action.LedColor;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.AbstractAction;
import edu.radboud.ai.roboud.action.LedColor;
import edu.radboud.ai.roboud.scenario.Scenario;
import java.util.Observer;
import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

public class LedAction extends AbstractAction {

    public LedAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void executeAction(Scenario scenario) {
        // TODO
        actionDone();
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        addObserver(abstractBehaviour);
        controller.sendCommand(kRobot_ShowMoodOff);
        controller.sendCommand(kRobot_HeartBeatOff);
        //Some how get color
        LedColor color = LedColor.BLUE;
        switch(color){
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
        }
        setChanged();
        notifyObservers();
    }
}
