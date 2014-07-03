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
    private String myTweet1;
    private String myTweet2;
    private ReadTextAction nrOfPeople;
    private String textGreetingEnd;

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
        ConfirmPostTweet = SpeechRepertoire.randomChoice(SpeechRepertoire.ConfirmPostTweet);
        textGreetingEnd = SpeechRepertoire.randomChoice(SpeechRepertoire.textGreetingEnd);
        myTweet1 = SpeechRepertoire.randomChoice(SpeechRepertoire.myTweet1);
        myTweet2 = SpeechRepertoire.randomChoice(SpeechRepertoire.myTweet2);
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
        //TODO: temporary solution:
        evaluateAssignment();
//        endConversation();
    }

    public void evaluateAssignment() {
        // ask if user succeeded with the assignment
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(AskUserSucceeded));
        }
        // not used now
        ReadTextAction temp = actionFactory.getReadTextAction(AskUserSucceeded);
        actions.add(temp);

        // ask nr of people
        if (scenario.isCanTalk()) {
            actions.add(actionFactory.getSpeakAction(AskNrOfPeople));
        }
        nrOfPeople = actionFactory.getReadTextAction(AskNrOfPeople);
        actions.add(nrOfPeople);

        // confirm nr of people
        if (scenario.isCanTalk()) {
            Log.i(TAG, ConfirmNrOfPeople);
            actions.add(actionFactory.getShowTextAction(ConfirmNrOfPeople));
            actions.add(actionFactory.getSpeakAction(ConfirmNrOfPeople));
        } else {
            actions.add(actionFactory.getShowTextAction(ConfirmNrOfPeople));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }

        if (scenario.isCanTalk()) {
//            ConfirmPostTweet += myTweet; //just for now, since it is not known
            actions.add(actionFactory.getShowTextAction(ConfirmPostTweet));
            actions.add(actionFactory.getSpeakAction(ConfirmPostTweet));
        } else {
            actions.add(actionFactory.getShowTextAction(ConfirmPostTweet));
            actions.add(actionFactory.getSleepAction(2500)); //this should be in ShowTextAction
        }
        endConversation();
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        if(currentAction == nrOfPeople) {
            String people = nrOfPeople.getInformation().toString();
            myTweet = myTweet1 + people + myTweet2;
//            postATweet();
        }
        return null;
    }

    public String getMyTweet(){ return myTweet;}

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
