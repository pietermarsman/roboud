package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ReadTextAction;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Guido on 02-07-14.
 */
public class CountNrPeopleAssignBehavior extends AbstractBehavior {

    public static final String TAG = "CountNrPeopleBehavior";
    private String askUserReady;
    private String askToCount;
    private String understand;
    private String ending;
    private String AskUserSucceeded;
    private String AskNrOfPeople;
    private String ConfirmNrOfPeople;
    private String ConfirmPostTweet;
    private String myTweet;
    private String myTweet1;
    private String myTweet2;
    private ReadTextAction nrOfPeople;
    private String textGreetingEnd;

    public CountNrPeopleAssignBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        // SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingStart);
        Log.v(TAG, "Initializing CountNrPeopleBehavior");
        askUserReady = SpeechRepertoire.randomChoice(SpeechRepertoire.questionAskUserReady);
        askToCount = SpeechRepertoire.randomChoice(SpeechRepertoire.textAskToCount);
        understand = SpeechRepertoire.randomChoice(SpeechRepertoire.questionUnderstand);
        ending = SpeechRepertoire.randomChoice(SpeechRepertoire.textEnding);

        AskUserSucceeded = SpeechRepertoire.randomChoice(SpeechRepertoire.AskUserSucceeded);
        AskNrOfPeople = SpeechRepertoire.randomChoice(SpeechRepertoire.AskNrOfPeople);
        ConfirmNrOfPeople = SpeechRepertoire.randomChoice(SpeechRepertoire.ConfirmNrOfPeople);
        ConfirmPostTweet = SpeechRepertoire.randomChoice(SpeechRepertoire.ConfirmPostTweet);
        textGreetingEnd = SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingEnd);
        myTweet1 = SpeechRepertoire.randomChoice(SpeechRepertoire.myTweet1);
        myTweet2 = SpeechRepertoire.randomChoice(SpeechRepertoire.myTweet2);

        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(askUserReady));
        }
        actions.add(actionFactory.getConfirmationAction(askUserReady));

        // tell user to count the number of people at conference, or where he is.
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(askToCount));
            actions.add(actionFactory.getSpeakAction(askToCount));
        } else {
            actions.add(actionFactory.getShowTextAction(askToCount));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }

        // ask whether user understands
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(understand));
        }
        actions.add(actionFactory.getConfirmationAction(understand));

        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(ending));
            actions.add(actionFactory.getSpeakAction(ending));
        } else {
            actions.add(actionFactory.getShowTextAction(ending));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }

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
