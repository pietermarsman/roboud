package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.MotorAction;
import edu.radboud.ai.roboud.action.RobotDirection;
import edu.radboud.ai.roboud.action.RobotSpeed;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by Guido Faassen on 20-06-14.
 */
public class RandomWander extends AbstractBehavior {
    RobotDirection direction;
    RobotSpeed speed;

    public RandomWander(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
    }

    public void wander() {
        while (true) {
            direction = RobotDirection.random();
            speed = RobotSpeed.random();
            MotorAction motorAction = new MotorAction(controller, direction, speed);
            blocks.add(motorAction);
        }
    }
}
