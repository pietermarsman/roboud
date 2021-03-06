package edu.radboud.ai.roboud.action.actions;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;

/**
 * Created by mikel_000 on 20-6-2014.
 */
public class SleepAction extends AbstractAction {

    private final static String TAG = "SleepAction";

    private long time;

    public SleepAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        if (information != null && information instanceof Long) {
            time = (Long) information;
        }
        if (time == 0) {
            throw new NullPointerException("Time cannot be 0");
        }
        Log.d(TAG, "Going to sleep");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Log.e(TAG, "sleep failed", e);
        }
        Log.d(TAG, "Waking up and notifying");
        setChanged();
        notifyObservers();
    }

    @Override
    public Object getInformation() {
        return null;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
