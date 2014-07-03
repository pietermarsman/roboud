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
public class AreWeFamiliarBehavior extends AbstractBehavior {

    public static final String TAG = "AreWeFamiliarBehavior";
    private boolean familiar;

    public AreWeFamiliarBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);

        String greetings = SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingStart);
        String shakeHands = SpeechRepertoire.randomChoice(SpeechRepertoire.shakeHands);
        String knowYou = SpeechRepertoire.randomChoice(SpeechRepertoire.knowYou);

        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(greetings));
            actions.add(actionFactory.getSpeakAction(greetings));
            actions.add(actionFactory.getShowTextAction(shakeHands));
            actions.add(actionFactory.getSpeakAction(shakeHands));
            actions.add(actionFactory.getShakeHandsAction());
            actions.add(actionFactory.getSpeakAction(knowYou));
        }
        else{
            actions.add(actionFactory.getShowTextAction(greetings));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
            actions.add(actionFactory.getShowTextAction(shakeHands));
            actions.add(actionFactory.getShakeHandsAction());
        }
        actions.add(actionFactory.getConfirmationAction(knowYou));
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        if (currentAction instanceof ConfirmationAction) {
            ConfirmationAction confirm = (ConfirmationAction) currentAction;
            familiar = confirm.getResult();
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
