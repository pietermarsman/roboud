package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SelectExistingUserBehavior extends AbstractBehavior {

    public static final String TAG = "SelectExistingUserBehavior";

    private String result;

    public SelectExistingUserBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        //TODO retrieve list of existing users
        List<String> users = new LinkedList<String>();
        users.add("Mike");
        users.add("Guido");
        users.add("Pieter");
        actions.add(actionFactory.getChoiceAction(users));
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
