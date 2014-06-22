package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.ListenAction;
import edu.radboud.ai.roboud.action.actions.ShowTextAction;
import edu.radboud.ai.roboud.action.actions.SpeakAction;
import edu.radboud.ai.roboud.scenario.Scenario;
import twitter4j.TwitterException;

import java.io.IOException;

/**
 * Created by Mike Ligthart on 18-6-2014.
 */
public class TaskFactory {

    private final static String TAG = "TaskFactory";
    private static TaskFactory instance = null;
    private Scenario scenario;
    private RoboudController controller;

    private TaskFactory(Scenario scenario, RoboudController controller) {
        this.scenario = scenario;
        this.controller = controller;
    }

    public synchronized static TaskFactory getInstance(Scenario scenario, RoboudController controller) {
        if (instance == null || !instance.equals(scenario))
            instance = new TaskFactory(scenario, controller);
        return instance;
    }

    public AskQuestionTask getAskQuestionTask(String question) {
        if (scenario.isCanTalk() && scenario.isCanListen()) {
            return new OutloudAskQuestionTask(controller, question);
        }
        else{
            return new ScreenAskQuestionTask(controller, question);
        }
    }

    public AskQuestionTask getAskQuestionTask(String[] questions) {
        if (scenario.isCanTalk() && scenario.isCanListen()) {
            return new OutloudAskQuestionTask(controller, questions);
        }
        else{
            return new ScreenAskQuestionTask(controller, questions);
        }
    }

    public LookForFacesTask getLookForFacesTask() {
        return new LookForFacesTask(controller, scenario.isCanDrive());
    }

    public MakeNewAppointmentTask getMakeNewAppointmentTask() {
        // TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public PostTweetTask getPostTweetTask(String text) {
        if (scenario.isHasInternet()) {
            try {
                return new PostTweetTask(controller, text);
            } catch (TwitterException e) {
                Log.e(TAG, "Twitter failed to post message", e);
                //TODO give feedback to user
                throw new UnsupportedOperationException("Not implemented yet");
            } catch (IOException e) {
                Log.e(TAG, "Twitter failed to post message", e);
                //TODO give feedback to user
                throw new UnsupportedOperationException("Not implemented yet");
            }

        } else {
            //What to do when no internet;
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }

    public DutchFlagLedTask getDutchFlagLedTask() {
        return new DutchFlagLedTask(controller);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }


}
