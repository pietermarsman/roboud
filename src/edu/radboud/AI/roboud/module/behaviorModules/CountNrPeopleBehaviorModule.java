package edu.radboud.ai.roboud.module.behaviorModules;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.behaviour.behaviors.AbstractBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.CountNrPeopleBehavior;
import edu.radboud.ai.roboud.module.util.CountNrPeopleBehaviorPhase;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.Observable;

/**
 * Created by Guido on 02-07-14.
 */
public class CountNrPeopleBehaviorModule extends AbstractBehaviorModule {
    public static final String TAG = "CountNrPeopleBehaviourModule";

    private static CountNrPeopleBehaviorModule ourInstance = null;
    private CountNrPeopleBehaviorPhase phase;

    private CountNrPeopleBehaviorModule(RoboudController controller, Scenario scenario) {
        super(controller, scenario);
    }

    public synchronized static CountNrPeopleBehaviorModule getInstance(RoboudController controller, Scenario scenario) {
        if (ourInstance == null)
            ourInstance = new CountNrPeopleBehaviorModule(controller, scenario);
        return ourInstance;
    }

    //TODO: provide different scenarios, further, scenarios have to be instantiated (use setter, or provide variable, Pieter?).
    @Override
    protected AbstractBehavior firstBehavior() {
        // temporary solution?
        if(phase == null)
            phase = CountNrPeopleBehaviorPhase.GIVEASSIGNMENT;

        if(phase == CountNrPeopleBehaviorPhase.GIVEASSIGNMENT){
            CountNrPeopleBehavior firstBehavior = behaviorFactory.getCountNrPeopleBehavior();
            // Is this the correct way to do it?
            firstBehavior.GiveAssignment();
            firstBehavior.addObserver(this);
            return firstBehavior;
        }
        else if(phase == CountNrPeopleBehaviorPhase.EVALUATEASSIGNMENT){
            CountNrPeopleBehavior firstBehavior = behaviorFactory.getCountNrPeopleBehavior();
            firstBehavior.EvaluateAssignment();
            firstBehavior.addObserver(this);
            return firstBehavior;
        }
        else
            throw new NullPointerException("CountNrPeopleBehaviorPhase not instantiated (1)");

    }

    @Override
    protected void stopBehavior() {
        if (currentBehavior != null) {
            currentBehavior.deleteObserver(this);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.i(TAG, "==CountNrPeople Module is updated== by " + observable.getClass().getSimpleName() + " and the phase is at " + phase);

        if(phase == CountNrPeopleBehaviorPhase.GIVEASSIGNMENT || observable instanceof CountNrPeopleBehavior){
            CountNrPeopleBehavior previousBehavior = (CountNrPeopleBehavior) observable;
            previousBehavior.deleteObserver(this);
            Log.i(TAG, "Phase is set to GIVEASSIGNMENT");
            currentBehavior = behaviorFactory.getExistingUserBehavior();
//            currentBehavior.GiveAssignment();
            currentBehavior.addObserver(this);
            behaviorReady = true;
        }
        else if(phase == CountNrPeopleBehaviorPhase.EVALUATEASSIGNMENT){

        }
        else
            throw new NullPointerException("CountNrPeopleBehaviorPhase not instantiated (2)");
    }
}
