package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.ConfirmationAction;
import edu.radboud.ai.roboud.action.util.LedColor;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;
import edu.radboud.ai.roboud.task.tasks.DutchFlagLedTask;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class AreWeFamiliarBehavior extends AbstractBehavior {

    public static final String TAG = "AreWeFamiliarBehavior";
    private boolean familiar;

    public AreWeFamiliarBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        blocks.add(actionFactory.getConfirmationAction("Do we know each other?"));
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        if (currentBlock instanceof ConfirmationAction){
            ConfirmationAction confirm = (ConfirmationAction) currentBlock;
            familiar = confirm.getResult();
            Log.i(TAG, "familiar is " + familiar);
        }
        else{
            Log.w(TAG, "Unexpected update");
        }
        return null;
    }

    @Override
    public Object getInformation() {
        return familiar;
    }

    public boolean isFamiliar(){
        return familiar;
    }
}
