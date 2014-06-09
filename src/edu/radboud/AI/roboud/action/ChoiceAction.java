package edu.radboud.ai.roboud.action;

import android.content.Intent;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

import java.util.List;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class ChoiceAction extends AbstractAction implements ActivityResultProcessor {

    public static final String TAG = "ChoiceAction";
    public static final int REQUEST_CODE = 11;
    public static final String DATA_NAME = "Options";

    private List<String> options;

    private String resultString;

    public ChoiceAction(RoboudController controller, List<String> options) {
        super(controller);
        this.options = options;
    }

    @Override
    public void executeAction(Scenario scenario) {
        Intent i = new Intent(controller, ChoiceActionActivity.class);
        String[] optionsArray = new String[options.size()];
        optionsArray = options.toArray(optionsArray);
        i.putExtra(DATA_NAME, optionsArray);
        controller.startNewActivityForResult(i, REQUEST_CODE, this);
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            resultString = data.getStringExtra(ChoiceActionActivity.RETURN_NAME);
            actionDone();
        } else {
            // TODO what todo if the activity stopped without letting the user choose
        }
    }

    public String getResultString() {
        return resultString;
    }
}
