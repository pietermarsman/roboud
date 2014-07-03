package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.util.HeadDirection;
import edu.radboud.ai.roboud.action.util.RobotDirection;
import edu.radboud.ai.roboud.action.util.RobotSpeed;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.Random;

/**
 * Created by Guido Faassen on 20-06-14.
 */
public class WanderBehavior extends AbstractBehavior {
    private RobotDirection direction;
    private RobotSpeed speed;
    private HeadDirection headDirection;
    private Random r;

    public WanderBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        speed = RobotSpeed.NORMAL;
        r = new Random();
    }

    public void forward() {
        Log.v(TAG, "forward");
        actions.add(actionFactory.getMotorAction(RobotDirection.FORWARD, speed));
    }

    public void lookLeftAndRight() {
        Log.v(TAG, "lookLeftAndRight");
        direction = RobotDirection.LEFT;
        actions.add(actionFactory.getMotorAction(direction, RobotSpeed.SLOW));
        direction = RobotDirection.RIGHT;
        actions.add(actionFactory.getMotorAction(direction, RobotSpeed.SLOW));
    }

    public void turnToRandomDirection() {
        Log.v(TAG, "turnToRandomDirection");
        if (r.nextInt(2) == 0)
            direction = RobotDirection.RIGHT;
        else
            direction = RobotDirection.LEFT;
        actions.add(actionFactory.getMotorAction(direction, speed));
    }

    public void headUpAndDown() {
        Log.v(TAG, "headUpAndDown");
        headDirection = HeadDirection.ALLUP;
        actions.add(actionFactory.getHeadAction(headDirection));

        // lichte aarzeling
        if (r.nextInt(10) < 5) {
            headDirection = HeadDirection.DOWN200;
            actions.add(actionFactory.getHeadAction(headDirection));
            headDirection = HeadDirection.UP200;
            actions.add(actionFactory.getHeadAction(headDirection));
        }

        headDirection = HeadDirection.ALLDOWN;
        actions.add(actionFactory.getHeadAction(headDirection));
        headDirection = HeadDirection.CENTER;
        actions.add(actionFactory.getHeadAction(headDirection));
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
