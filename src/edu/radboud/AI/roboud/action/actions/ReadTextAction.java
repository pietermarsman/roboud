package edu.radboud.ai.roboud.action.actions;

import android.app.Activity;
import android.content.Intent;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.ReadTextActionActivity;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class ReadTextAction extends AbstractAction implements ActivityResultProcessor {

    public static final int REQUEST_CODE = 1294;
    private static final String TAG = "ReadTextAction";
    private String result;

    public ReadTextAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        Intent i = new Intent(controller, ReadTextActionActivity.class);
        controller.startNewActivityForResult(i, REQUEST_CODE, this);
    }

    @Override
    public Object getInformation() {
        return result;
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            result = data.getStringExtra(ReadTextActionActivity.RETURN_NAME);
            setChanged();
            notifyObservers();
        } else {
            // TODO what todo if the activity stopped without letting the user choose
        }
    }
}
