package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.*;
import edu.radboud.ai.roboud.scenario.Scenario;
import twitter4j.TwitterException;

import java.io.IOException;

/**
 * Created by Mike Ligthart on 18-6-2014.
 */
public class TaskFactory {

    private final static String TAG = "TaskFactory";
    private Scenario scenario;
    private RoboudController controller;
    private static TaskFactory instance = null;

    private TaskFactory(Scenario scenario){
        this.scenario = scenario;
        this.controller = controller;
    }

    public synchronized static TaskFactory getInstance(Scenario scenario) {
        if (instance == null || !instance.equals(scenario))
            instance = new TaskFactory(scenario);
        return instance;
    }

    public AskQuestionTask getAskQuestionTask(String question) throws UnsupportedOperationException {
        AbstractAction output, input;
        if(scenario.isCanTalk()){
            output = new SpeakAction(controller, question);
        }
        else{
            output = new ShowTextAction(controller, question);
        }

        if(scenario.isCanListen()){
            input = new ListenAction(controller);
        }
        else{
            //TODO create readWritenTextAction
            throw new UnsupportedOperationException("Not implemented yet");
        }

        return new AskQuestionTask(question, output, input);
    }

    public AskQuestionTask getAskQuestionTask(String[] questions) throws UnsupportedOperationException {
        AbstractAction output, input;
        String question;
        if(scenario.isCanTalk()){
            output = new SpeakAction(controller, questions);
            SpeakAction temp = (SpeakAction) output;
            question = temp.getText();
        }
        else{
            output = new ShowTextAction(controller, questions);
            ShowTextAction temp = (ShowTextAction) output;
            question = temp.getText();
        }

        if(scenario.isCanListen()){
            input = new ListenAction(controller);
        }
        else{
            //TODO create readWritenTextAction
            throw new UnsupportedOperationException("Not implemented yet");
        }

        return new AskQuestionTask(question, output, input);
    }

    public LookForFacesTask getLookForFacesTask(){
        return new LookForFacesTask(scenario.isCanDrive());
    }

    public MakeNewAppointmentTask getMakeNewAppointmentTask(){
        // TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public PostTweetTask getPostTweetTask(String text){
        if (scenario.isHasInternet()){
            try {
                return new PostTweetTask(text);
            } catch (TwitterException e) {
                Log.e(TAG, "Twitter failed to post message", e);
                //TODO give feedback to user
                throw new UnsupportedOperationException("Not implemented yet");
            } catch (IOException e) {
               Log.e(TAG, "Twitter failed to post message", e);
                //TODO give feedback to user
                throw new UnsupportedOperationException("Not implemented yet");
            }

        }
        else {
            //What to do when no internet;
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }

    public Scenario getScenario(){
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }


}
