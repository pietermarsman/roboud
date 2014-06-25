package edu.radboud.ai.roboud.behaviour;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.MotorAction;
import edu.radboud.ai.roboud.action.pools.MotorActionPool;
import edu.radboud.ai.roboud.action.util.RobotDirection;
import edu.radboud.ai.roboud.action.util.RobotSpeed;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by Guido Faassen on 20-06-14.
 */
public class RandomWanderBehavior extends AbstractBehavior {
    private RobotDirection direction;
    private RobotSpeed speed;
    private MotorAction motorAction;

    public RandomWanderBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
        wander();
    }

    public void wander() {
//        for(int i=0;i<1;i++) {
        direction = RobotDirection.random();
        speed = RobotSpeed.random();
        Log.v(TAG, "Direction: " + direction.toString() + " Speed: " + speed);
        motorAction = MotorActionPool.getInstance(controller).acquire(direction, speed);
        blocks.add(motorAction);
//        }
    }

    @Override
    public void releaseActions() {
        MotorActionPool.getInstance(controller).release(motorAction);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        return null;
    }
}
