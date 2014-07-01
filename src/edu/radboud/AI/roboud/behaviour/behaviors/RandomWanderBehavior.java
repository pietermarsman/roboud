package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.util.RobotDirection;
import edu.radboud.ai.roboud.action.util.RobotSpeed;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Guido Faassen on 20-06-14.
 */
public class RandomWanderBehavior extends AbstractBehavior {
    private RobotDirection direction;
    private RobotSpeed speed;

    public RandomWanderBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        wander();
    }

    public void wander() {
        direction = RobotDirection.random();
        speed = RobotSpeed.random();
        Log.v(TAG, "Direction: " + direction.toString() + " Speed: " + speed);
        actions.add(actionFactory.getMotorAction(direction, speed));
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
