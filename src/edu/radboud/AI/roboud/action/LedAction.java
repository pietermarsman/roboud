package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;

import static com.wowwee.robome.RoboMeCommands.RobotCommand.*;

/**
 * @author Mike Ligthart
 */
public class LedAction extends AbstractAction {

    private LedColor color;

    public LedAction(RoboudController controller, LedColor color) {
        super(controller);
        this.color = color;
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        addObserver(abstractBehaviour);
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
        notifyObservers();
    }
}
