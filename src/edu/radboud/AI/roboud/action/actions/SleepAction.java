package edu.radboud.ai.roboud.action.actions;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;

/**
 * Created by mikel_000 on 20-6-2014.
 */
public class SleepAction extends AbstractAction {

    private final static String TAG = "SleepAction";

    private long time;

    public SleepAction(RoboudController controller, long time) {
        super(controller);
        this.time = time;
        Log.d(TAG, "SleepAction is created");
    }

    @Override
    public void doActions() {
        Log.d(TAG, "Going to sleep");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Log.e(TAG, "sleep failed", e);
        }
        setChanged();
        Log.d(TAG, "Waking up and notifying");
        notifyObservers();
    }
}
