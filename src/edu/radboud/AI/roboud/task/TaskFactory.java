package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.Action;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.ShowTextAction;
import edu.radboud.ai.roboud.action.SpeakAction;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Mike Ligthart on 18-6-2014.
 */
public class TaskFactory {

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

    public AskQuestionTask getAskQuestionTask(String text) throws UnsupportedOperationException {
        Action output, input;
        if(scenario.isCanTalk()){
            output = new SpeakAction(controller, text);
        }
        else{
            output = new ShowTextAction(controller, text);
        }

        if(scenario.isCanListen()){
            input = new ListenAction(controller);
        }
        else{
            //TODO create readWritenTextAction
            throw new UnsupportedOperationException("Not implemented yet");
        }

        return new AskQuestionTask(output, input);
    }

    public LookForFacesTask getLookForFacesTask(){
        // TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public MakeNewAppointmentTask getMakeNewAppointmentTask(){
        // TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public PostTweetTask getPostTweetTask(){
        // TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Scenario getScenario(){
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }


}
