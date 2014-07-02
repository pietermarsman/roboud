package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Guido on 02-07-14.
 */
public class CountNrPeopleBehavior extends AbstractBehavior {

    public static final String TAG = "CountNrPeopleBehavior";

    public CountNrPeopleBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        // SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingStart);
        String tellUser = SpeechRepertoire.randomChoice(SpeechRepertoire.questionAskUserReady);
        String askToCount = SpeechRepertoire.randomChoice(SpeechRepertoire.textAskToCount);
        String understand = SpeechRepertoire.randomChoice(SpeechRepertoire.questionUnderstand);
        String ending = SpeechRepertoire.randomChoice(SpeechRepertoire.textEnding);

        // ask if user is ready
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(tellUser));
        }
        actions.add(actionFactory.getReadTextAction(tellUser));

        // tell user to count the number of people at conference, or where he is.
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(askToCount));
            actions.add(actionFactory.getSpeakAction(askToCount));
        } else {
            actions.add(actionFactory.getShowTextAction(askToCount));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }

        // ask whether user understands
        //TODO: What if answer is no? Get feedback and loop back (max n times).
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(understand));
        }
        actions.add(actionFactory.getReadTextAction(understand));

        // end conversation
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(ending));
        }
        actions.add(actionFactory.getReadTextAction(ending));


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
