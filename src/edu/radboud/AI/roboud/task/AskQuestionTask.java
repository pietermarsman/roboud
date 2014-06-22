package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.AbstractAction;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public abstract class AskQuestionTask extends AbstractTask {

    private final static String TAG = "AskQuestionTask";
    protected String question, answer;

    public AskQuestionTask(RoboudController controller, String question) {
        super(controller);
        this.question = question;
        answer = "";
    }

    public AskQuestionTask(RoboudController controller) {
        super(controller);
        answer = "";
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
