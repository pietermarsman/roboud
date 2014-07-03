package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehavior extends AbstractBehavior {

    public static final String TAG = "IntroduceBehavior";

    public IntroduceBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);

        //These need to be consistent in both text and speech
        String greetings = SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingStart);
        String introduceMySelf = SpeechRepertoire.randomChoice(SpeechRepertoire.textIntroduceMyself);
        String yourName = SpeechRepertoire.randomChoice(SpeechRepertoire.questionName);
        String yourAge = SpeechRepertoire.randomChoice(SpeechRepertoire.questionAge);
        String yourSex = SpeechRepertoire.randomChoice(SpeechRepertoire.questionSex);
        String enough = "Oké, now I know enough about you";
        String ending = SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingEnd);

        //Greetings and introducing myself
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(greetings));
            actions.add(actionFactory.getSpeakAction(greetings));
            actions.add(actionFactory.getShowTextAction(introduceMySelf));
            actions.add(actionFactory.getSpeakAction(introduceMySelf));
        } else {
            actions.add(actionFactory.getShowTextAction(greetings));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
            actions.add(actionFactory.getShowTextAction(introduceMySelf));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }

        //Ask name
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(yourName));
        }
        actions.add(actionFactory.getReadTextAction(yourName));

        //Ask age
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(yourAge));
        }
        actions.add(actionFactory.getReadTextAction(yourAge));

        //Ask sex
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(yourSex));
        }
        actions.add(actionFactory.getReadTextAction(yourSex));

        //End introduce
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(enough));
            actions.add(actionFactory.getSpeakAction(enough));
            actions.add(actionFactory.getShowTextAction(ending));
            actions.add(actionFactory.getSpeakAction(ending));
        } else {
            actions.add(actionFactory.getShowTextAction(enough));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
            actions.add(actionFactory.getShowTextAction(ending));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }
        //TODO the final action is save action that stores relevant parts in the results hashtable by using the below processInformation method?
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
