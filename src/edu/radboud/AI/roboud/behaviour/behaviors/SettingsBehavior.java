package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ConfirmationAction;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SettingsBehavior extends AbstractBehavior {

    private String drive, turn;

    public SettingsBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        drive = SpeechRepertoire.randomChoice(SpeechRepertoire.drive);
        turn = SpeechRepertoire.randomChoice(SpeechRepertoire.turn);
        actions.add(actionFactory.getConfirmationAction(drive));
        actions.add(actionFactory.getConfirmationAction(turn));
    }


    @Override
    protected Object processInformation(AbstractAction currentAction) {
        if (currentAction instanceof ConfirmationAction){
            ConfirmationAction confirmationAction = (ConfirmationAction) currentAction;
            if (confirmationAction.getQuestion().contentEquals(drive)) {
                if (confirmationAction.getResult()){
                    scenario.setCanWander(true);
                    Log.i(TAG, "Can Wander is true");
                    return false;
                }
                else {
                    scenario.setCanWander(false);
                    Log.i(TAG, "Can wander is false;");
                }
            }
            else if(confirmationAction.getQuestion().contentEquals(turn)){
                if (confirmationAction.getResult()){
                    scenario.setCanTurn(true);
                }
                else{
                    scenario.setCanTurn(false);
                }
            }

        }
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
