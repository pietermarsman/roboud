package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.AbstractAction;
import edu.radboud.ai.roboud.action.Action;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.SpeakAction;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AskQuestionTask extends AbstractTask {

    private final static String TAG = "AskQuestionTask";
    private String question, answer;

    public AskQuestionTask(String question, AbstractAction output, AbstractAction input) {
        this.question = question;
        answer = "";
        input.addObserver(this);
        actions.add(output);
        actions.add(input);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            answer = (String) data;
            Log.i(TAG, "Answer = " + answer);
            setChanged();
            notifyObservers(answer);
        }
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }
}
