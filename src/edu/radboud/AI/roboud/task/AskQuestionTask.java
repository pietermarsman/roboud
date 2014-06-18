package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.Action;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.SpeakAction;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AskQuestionTask extends AbstractTask implements Observer {

    private String answer;

    public AskQuestionTask(Action output, Action input) {
        actions.add(output);
        actions.add(input);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            answer = (String) data;
            setChanged();
            notifyObservers(answer);
        }
    }
}
