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
        speed = RobotSpeed.FASTEST;
    }

//    public void wander() {
////        Log.v(TAG, "Direction: " + direction.toString() + " Speed: " + speed);
//
//        // Get some randomness in random behaviour!
//        Random r1 = new Random(10);
//        while(true){
//            forward();
//            if(r1.nextInt() < 5)
//                lookLeftAndRight();
//            headUpAndDown();
//            turnToRandomDirection();
//
//            // tilt head.
//        }
//    }

    public void forward(){
        Log.v(TAG, "Moving forward:");
        actions.add(actionFactory.getMotorAction(RobotDirection.FORWARD, speed));
    }

    private void lookLeftAndRight(){
        direction = RobotDirection.LEFT;
        actions.add(actionFactory.getMotorAction(direction, RobotSpeed.SLOW));
        direction = RobotDirection.RIGHT;
        actions.add(actionFactory.getMotorAction(direction, RobotSpeed.SLOW));
    }

    public void turnToRandomDirection(){
        direction = RobotDirection.random();
        actions.add(actionFactory.getMotorAction(direction, speed));
    }

    public void headUpAndDown(){

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
