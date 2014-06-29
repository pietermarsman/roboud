package edu.radboud.ai.roboud.behaviour.behaviors;

import android.app.Notification;
import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.MotorAction;
import edu.radboud.ai.roboud.action.pools.MotorActionPool;
import edu.radboud.ai.roboud.action.util.RobotDirection;
import edu.radboud.ai.roboud.action.util.RobotSpeed;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Guido Faassen on 20-06-14.
 */
public class RandomWanderBehavior extends AbstractBehavior {
    private RobotDirection direction;
    private RobotSpeed speed;
    private MotorAction motorAction;
    public RandomWanderBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        wander();
    }

    public void wander() {
        direction = RobotDirection.random();
        speed = RobotSpeed.random();
        Log.v(TAG, "Direction: " + direction.toString() + " Speed: " + speed);
        blocks.add(actionFactory.getMotorAction(direction, speed));
    }
    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
