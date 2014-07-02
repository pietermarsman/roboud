package edu.radboud.ai.roboud.module.behaviorModules;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.behaviour.behaviors.AbstractBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.RandomWanderBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.TurnMeOffBehavior;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.Observable;


/**
 * Created by mikel_000 on 29-6-2014.
 */
public class TurnMeOffBehaviorModule extends AbstractBehaviorModule {


    private static TurnMeOffBehaviorModule ourInstance = null;

    private TurnMeOffBehaviorModule(RoboudController controller, Scenario scenario) {
        super(controller, scenario);
    }

    public synchronized static TurnMeOffBehaviorModule getInstance(RoboudController controller, Scenario scenario) {
        if (ourInstance == null)
            ourInstance = new TurnMeOffBehaviorModule(controller, scenario);
        return ourInstance;
    }

    @Override
    protected AbstractBehavior firstBehavior() {
        TurnMeOffBehavior firstBehavior = behaviorFactory.getTurnMeOffBehavior();
        firstBehavior.addObserver(this);
        //controller.getModel().addObserver(this);
        return firstBehavior;
    }

    @Override
    protected void stopBehavior() {
        currentBehavior.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AbstractBehavior) {
            AbstractBehavior behavior = (AbstractBehavior) observable;
            behavior.deleteObserver(this);
            RandomWanderBehavior wander = behaviorFactory.getRandomWanderBehavior();
            if (controller.getModel().isDistance_50() || controller.getModel().isDistance_20()){
                Log.i(TAG, "Turn");
                wander.turnToRandomDirection();
            }
            else {
                Log.i(TAG, "Forward");
                wander.forward();
            }

            currentBehavior = wander;
            currentBehavior.addObserver(this);
            behaviorReady = true;
        }
        /*
        if (observable instanceof RoboudModel){
            Integer tempDistance = controller.getModel().getRobomeSensorStatus();
            Log.i(TAG,"TempDistance: " + tempDistance);
            if (tempDistance != -1 && tempDistance != distanceToRobome){
                RandomWanderBehavior wander = behaviorFactory.getRandomWanderBehavior();
                distanceToRobome = tempDistance;
                if (distanceToRobome <= threshold){
                    wander.turnToRandomDirection();
                }
                else{
                    wander.forward();
                }
                currentBehavior = wander;
                currentBehavior.addObserver(this);
                behaviorReady = true;
            }
        }
        */
    }
}
