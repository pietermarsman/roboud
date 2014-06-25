package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.SleepAction;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class SleepActionPool extends ActionPool<SleepAction> {

    private static SleepActionPool instance = null;

    private SleepActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized SleepActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new SleepActionPool(controller);
        return instance;
    }

    public SleepAction acquire(long time) {
        SleepAction sleepAction = acquire();
        sleepAction.setTime(time);
        return sleepAction;
    }

    @Override
    protected SleepAction create() {
        return new SleepAction(controller);
    }
}
