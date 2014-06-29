package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;
import edu.radboud.ai.roboud.action.actions.ConfirmationAction;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SelectExistingUserBehavior extends AbstractBehavior {

    public static final String TAG = "SelectExistingUserBehavior";

    private String result;

    public SelectExistingUserBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        //TODO retrieve list of existing users
        List<String> users = new LinkedList<String>();
        users.add("Mike");
        users.add("Guido");
        users.add("Pieter");
        blocks.add(actionFactory.getChoiceAction(users));
    }


    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        if (currentBlock instanceof ChoiceAction){
            ChoiceAction choice = (ChoiceAction) currentBlock;
            result = choice.getResultString();
            Log.i(TAG, "result is " + choice.getResultString());
        }
        else{
            Log.w(TAG, "Unexpected update");
        }
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }

    public String getResult(){
        return result;
    }
}
