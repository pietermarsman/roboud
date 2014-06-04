package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.SpeakAction;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AskQuestionTask extends AbstractTask implements Observer {

    String answer;

    public AskQuestionTask(RoboudController controller, String question) {
        actions.add(new SpeakAction(controller, question));
        actions.add(new ListenAction(controller));
    }

    public AskQuestionTask(RoboudController controller, String[] questions) {
        this(controller, SpeechRepertoire.randomChoice(questions));
    }

    @Override
    public boolean isSuitable(Scenario scenario) {
        // TODO
        return true;
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
