package edu.radboud.ai.roboud.module.behaviorModules;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.behaviour.behaviors.AbstractBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.RandomWanderBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.TurnMeOffBehavior;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.Observable;
import java.util.Random;


/**
 * Created by mikel_000 on 29-6-2014.
 */
public class TurnMeOffBehaviorModule extends AbstractBehaviorModule {

    private static TurnMeOffBehaviorModule ourInstance = null;
    Random r;

    private TurnMeOffBehaviorModule(RoboudController controller, Scenario scenario) {
        super(controller, scenario);
        r = new Random();
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
            if (controller.getModel().isDistance_50() || controller.getModel().isDistance_20()) {
                Log.i(TAG, "Turn");

                // sometimes look up and down
                if (r.nextInt(10) < 6)
                    wander.headUpAndDown();

                // always turn
                wander.turnToRandomDirection();

                // sometimes take a look around
                if (r.nextInt(10) < 4)
                    wander.lookLeftAndRight();
            } else {
                Log.i(TAG, "Before starting forward");
                wander.forward();
            }

            currentBehavior = wander;
            currentBehavior.addObserver(this);
            behaviorReady = true;
        }
    }
}
