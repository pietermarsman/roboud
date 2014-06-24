package edu.radboud.ai.roboud.action.actions;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class ReadTextAction extends AbstractAction implements Observer {

    private static final String TAG = "ReadTextAction";
    private ArrayList<String> results;

    public ReadTextAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions() {
        Log.w(TAG, "using a not implemented function");
        results.add("This is not implemented yet");
        setChanged();
        notifyObservers(results);
    }

    @Override
    public void update(Observable observable, Object data) {
        /*Log.d(TAG, "Update received from " + observable.getClass().toString());
        if (observable instanceof AndroidMicrophone) {
            results = (ArrayList<String>) data;
            Log.d(TAG, "Result of ListenAction is: " + results);

        }
        */
    }


    public ArrayList<String> getResult() {
        return results;
    }
}
