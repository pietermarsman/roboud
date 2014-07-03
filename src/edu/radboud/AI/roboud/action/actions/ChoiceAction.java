package edu.radboud.ai.roboud.action.actions;

import android.app.Activity;
import android.content.Intent;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.ChoiceActionActivity;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

import java.util.List;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class ChoiceAction extends AbstractAction implements ActivityResultProcessor {

    public static final String TAG = "ChoiceAction";
    public static final int REQUEST_CODE = 11;

    private List<String> options;
    private String question;

    private String resultString;

    public ChoiceAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        if (information != null && information instanceof List) {
            options = (List) information;
        }

        if (options == null) {
            throw new NullPointerException("Options not properly initialized");
        }
        Intent i = new Intent(controller, ChoiceActionActivity.class);
        String[] optionsArray = new String[options.size()];
        optionsArray = options.toArray(optionsArray);
        i.putExtra(ChoiceActionActivity.EXTRAS_OPTIONS, optionsArray);
        i.putExtra(ChoiceActionActivity.EXTRAS_TEXT, question);
        controller.startNewActivityForResult(i, REQUEST_CODE, this);
    }

    @Override
    public Object getInformation() {
        return resultString;
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            resultString = data.getStringExtra(ChoiceActionActivity.RETURN_NAME);
            setChanged();
            notifyObservers();
        } else {
            // TODO what todo if the activity stopped without letting the user choose
        }
    }

    public String getResultString() {
        return resultString;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
