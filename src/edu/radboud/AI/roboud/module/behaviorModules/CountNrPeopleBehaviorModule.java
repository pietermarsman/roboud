package edu.radboud.ai.roboud.module.behaviorModules;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.behaviour.behaviors.AbstractBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.CountNrPeopleBehavior;
import edu.radboud.ai.roboud.behaviour.behaviors.SettingsBehavior;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.Observable;

import static edu.radboud.ai.roboud.module.util.CountNrPeopleBehaviorPhase.EVALUATEASSIGNMENT;
import static edu.radboud.ai.roboud.module.util.CountNrPeopleBehaviorPhase.GIVEASSIGNMENT;

/**
 * Created by Guido on 02-07-14.
 */
public class CountNrPeopleBehaviorModule extends AbstractBehaviorModule {
    public static final String TAG = "CountNrPeopleBehaviourModule";

    private static CountNrPeopleBehaviorModule ourInstance = null;

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
        RoboudModel model = controller.getModel();
        CountNrPeopleBehavior firstBehavior;
        if (model.getCountNrPeopleBehaviorPhase() == null)
            model.setCountNrPeopleBehaviorPhase(GIVEASSIGNMENT);
        switch (model.getCountNrPeopleBehaviorPhase()) {
            case GIVEASSIGNMENT:
                firstBehavior = behaviorFactory.getCountNrPeopleBehavior();
                // Is this the correct way to do it?
                firstBehavior.giveAssignment();
                firstBehavior.addObserver(this);
                break;
            case EVALUATEASSIGNMENT:
                firstBehavior = behaviorFactory.getCountNrPeopleBehavior();
                firstBehavior.evaluateAssignment();
                firstBehavior.addObserver(this);
                break;
            default:
                // This should never happen.
                Log.e(TAG, "CountNrPeopleBehaviorPhase was not initialized but should be");
                firstBehavior = null;
        }
        return firstBehavior;
    }

    @Override
    protected void stopBehavior() {
        if (currentBehavior != null) {
            currentBehavior.deleteObserver(this);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.i(TAG, "==CountNrPeople Module is updated== by " + observable.getClass().getSimpleName()
                + " and the phase is at " + controller.getModel().getCountNrPeopleBehaviorPhase());
        Log.i(TAG, "This update is not required, because shutdown is required?");

        RoboudModel model = controller.getModel();
        if (model.getCountNrPeopleBehaviorPhase() == GIVEASSIGNMENT || observable instanceof CountNrPeopleBehavior) {
            CountNrPeopleBehavior previousBehavior = (CountNrPeopleBehavior) observable;
            previousBehavior.deleteObserver(this);
            Log.i(TAG, "Phase is set to GIVEASSIGNMENT");
            currentBehavior = behaviorFactory.getExistingUserBehavior(controller);
//            currentBehavior.giveAssignment();
            currentBehavior.addObserver(this);
            behaviorReady = true;
        } else if (model.getCountNrPeopleBehaviorPhase() == EVALUATEASSIGNMENT) {
            SettingsBehavior previousBehavior = (SettingsBehavior) observable;
            previousBehavior.deleteObserver(this);
            Log.i(TAG, "and we're finished with counting people");
            finished();
        } else
            throw new NullPointerException("CountNrPeopleBehaviorPhase not instantiated (2)");
    }
}
