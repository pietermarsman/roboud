package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;
import edu.radboud.ai.roboud.action.util.LedColor;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.List;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SelectExistingUserBehavior extends AbstractBehavior {

    public static final String TAG = "SelectExistingUserBehavior";

    private String result;
    private boolean success;

    public SelectExistingUserBehavior(ActionFactory actionFactory, Scenario scenario, RoboudController controller) {
        super(actionFactory, scenario);

        RoboudModel model = controller.getModel();
        List<String> userNames = model.getUserNames();
        actions.add(actionFactory.getLedAction(LedColor.GREEN));
        if (userNames.isEmpty()){
            actions.add(actionFactory.getLedAction(LedColor.YELLOW));
            actions.add(actionFactory.getShowTextAction("Oh oh. I don't think we know each other. Let me introduce myself"));
            if (scenario.isCanTalk()){
                actions.add(actionFactory.getSpeakAction("Oh oh. I don't think we know each other. Let me introduce myself"));
            }
            success = false;
        }
        else {
            if (scenario.isCanTalk()){
                actions.add(actionFactory.getSpeakAction("Please Select your name"));
            }
            actions.add(actionFactory.getChoiceAction(model.getUserNames()));
            success = true;
        }
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
        return success;
    }

    public boolean isSuccess(){
        return success;
    }
    public String getResult() {
        return result;
    }
}
