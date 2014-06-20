package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.action.AbstractAction;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AskQuestionTask extends AbstractTask {

    private final static String TAG = "AskQuestionTask";
    private String question, answer;

    public AskQuestionTask(String question, AbstractAction output, AbstractAction input) {
        super();
        this.question = question;
        answer = "";
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
        super.update(observable, data);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
