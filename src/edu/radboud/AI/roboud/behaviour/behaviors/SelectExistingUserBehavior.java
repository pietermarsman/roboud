package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SelectExistingUserBehavior extends AbstractBehavior {

    public static final String TAG = "SelectExistingUserBehavior";

    private String result;

    public SelectExistingUserBehavior(ActionFactory actionFactory, Scenario scenario, RoboudController controller) {
        super(actionFactory, scenario);

        RoboudModel model = controller.getModel();
        actions.add(actionFactory.getChoiceAction(model.getUserNames()));
    }


    @Override
    protected Object processInformation(AbstractAction currentAction) {
        if (currentAction instanceof ChoiceAction) {
            ChoiceAction choice = (ChoiceAction) currentAction;
            result = choice.getResultString();
            Log.i(TAG, "result is " + choice.getResultString());
        } else {
            Log.w(TAG, "Unexpected update");
        }
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }

    public String getResult() {
        return result;
    }
}
