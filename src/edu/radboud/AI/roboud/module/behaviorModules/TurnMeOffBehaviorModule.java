package edu.radboud.ai.roboud.module.behaviorModules;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.behaviour.behaviors.AbstractBehavior;
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
            currentBehavior = behaviorFactory.getRandomWanderBehavior();
            currentBehavior.addObserver(this);
            behaviorReady = true;
        }
    }
}
