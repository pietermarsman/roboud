package edu.radboud.ai.roboud.action.actions;

import android.app.Activity;
import android.content.Intent;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.ChoiceActionActivity;
import edu.radboud.ai.roboud.action.util.ConfirmationActionActivity;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

/**
 * Created by Pieter Marsman on 9-6-2014.
 */
public class ConfirmationAction extends AbstractAction implements ActivityResultProcessor {

    public static final String TAG = "ConfirmationAction";
    public static final int REQUEST_CODE = 12;
    public static final String DATA_NAME = "Question";

    private String question;
    private boolean result;

    @Deprecated
    public ConfirmationAction(RoboudController controller, String question) {
        super(controller);
        this.question = question;
    }

    public ConfirmationAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            result = data.getBooleanExtra(ChoiceActionActivity.RETURN_NAME, false);
            setChanged();
            notifyObservers();
        } else {
            // TODO what todo if the activity stopped without letting the user choose
        }
    }

    @Override
    public void doActions(Object information) {
        if (information != null && information instanceof String) {
            question = (String) information;
        }
        if (question == null) {
            throw new NullPointerException("Question cannot be null");
        }
        Intent i = new Intent(controller, ConfirmationActionActivity.class);
        i.putExtra(DATA_NAME, question);
        controller.startNewActivityForResult(i, REQUEST_CODE, this);
    }

    @Override
    public Object getInformation() {
        return result;
    }

    public boolean getResult() {
        return result;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
