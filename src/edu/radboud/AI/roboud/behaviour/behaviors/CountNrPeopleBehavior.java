package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ReadTextAction;
import edu.radboud.ai.roboud.behaviour.util.PostTweet;
import edu.radboud.ai.roboud.behaviour.util.SpeechRepertoire;
import edu.radboud.ai.roboud.util.Scenario;
import twitter4j.TwitterException;

import java.io.IOException;

/**
 * Created by Guido on 02-07-14.
 */
public class CountNrPeopleBehavior extends AbstractBehavior {

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

    public CountNrPeopleBehavior(ActionFactory actionFactory, Scenario scenario) {
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
        ConfirmPostTweet = SpeechRepertoire.randomChoice(SpeechRepertoire.ConfirmPostTweet) + "Default tweet";
        // ending
    }

    public void giveAssignment() {
        // ask if user is ready
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(askUserReady));
        }
        actions.add(actionFactory.getReadTextAction(askUserReady));

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
            ReadTextAction temp = actionFactory.getReadTextAction(understand);
            actions.add(temp);

        endConversation();
    }

    public void evaluateAssignment() {
        // ask if user succeeded with the assignment
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(AskUserSucceeded));
        }
        ReadTextAction temp = actionFactory.getReadTextAction(AskUserSucceeded);
        String succeeded = temp.getInformation().toString();
        actions.add(temp);

        if (succeeded.equals("no") || succeeded.equals("No") || succeeded.equals("No ") || succeeded.equals("no ")) {
            endConversation();
        } else {
            // ask nr of people
            if (scenario.isCanTalk()) {
                actions.add(actionFactory.getSpeakAction(AskNrOfPeople));
            }
            temp = actionFactory.getReadTextAction(AskNrOfPeople);
            actions.add(temp);

            // confirm nr of people
            if (scenario.isCanTalk()) {
//                ConfirmNrOfPeople += nrOfPeople;
                Log.i(TAG, ConfirmNrOfPeople);
                actions.add(actionFactory.getShowTextAction(ConfirmNrOfPeople));
                actions.add(actionFactory.getSpeakAction(ConfirmNrOfPeople));
            } else {
                actions.add(actionFactory.getShowTextAction(ConfirmNrOfPeople));
                actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
            }

            myTweet = "I was at a conference with " + nrOfPeople + " people. It was great!";

            //confirm posting tweet
            postATweet();
            if (scenario.isCanTalk()) {
                ConfirmPostTweet += myTweet;
                actions.add(actionFactory.getShowTextAction(ConfirmPostTweet));
                actions.add(actionFactory.getSpeakAction(ConfirmPostTweet));
            } else {
                actions.add(actionFactory.getShowTextAction(ConfirmPostTweet));
                actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
            }
        }
        endConversation();
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        return null;
    }

    private void postATweet(){
        PostTweet postMyTweet;
        try {
            postMyTweet = new PostTweet();
            postMyTweet.postTweet(myTweet);
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endConversation()
    {
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getShowTextAction(ending));
            actions.add(actionFactory.getSpeakAction(ending));
        } else {
            actions.add(actionFactory.getShowTextAction(ending));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }
    }

    @Override
    public Object getInformation() {
        return null;
    }
}