package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ConfirmationAction;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class AreWeFamiliarBehavior extends AbstractBehavior {

    public static final String TAG = "AreWeFamiliarBehavior";
    private boolean familiar;

    public AreWeFamiliarBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        actions.add(actionFactory.getConfirmationAction("Do we know each other?"));
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        if (currentAction instanceof ConfirmationAction) {
            ConfirmationAction confirm = (ConfirmationAction) currentAction;
            familiar = confirm.getResult();
            Log.i(TAG, "familiar is " + familiar);
        } else {
            Log.w(TAG, "Unexpected update");
        }
        return null;
    }

    @Override
    public Object getInformation() {
        return familiar;
    }

    public boolean isFamiliar() {
        return familiar;
    }
}
