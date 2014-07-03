package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Guido on 02-07-14.
 */
public class CountNrPeopleBehavior extends AbstractBehavior {

    public static final String TAG = "CountNrPeopleBehavior";
    private String tellUser;
    private String askToCount;
    private String understand;
    private String ending;

    public CountNrPeopleBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        // SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingStart);
        Log.v(TAG, "Initializing CountNrPeopleBehavior");
        tellUser = SpeechRepertoire.randomChoice(SpeechRepertoire.questionAskUserReady);
        askToCount = SpeechRepertoire.randomChoice(SpeechRepertoire.textAskToCount);
        understand = SpeechRepertoire.randomChoice(SpeechRepertoire.questionUnderstand);
        ending = SpeechRepertoire.randomChoice(SpeechRepertoire.textEnding);


    }

    public void giveAssignment() {
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

    public void evaluateAssignment() {

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
