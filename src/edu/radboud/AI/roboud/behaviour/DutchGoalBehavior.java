package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.SleepAction;
import edu.radboud.ai.roboud.action.pools.SleepActionPool;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by mikel_000 on 25-6-2014.
 */
public class DutchGoalBehavior extends AbstractBehavior {

    public final static String TAG = "DutchGoalBehavior";
    private SleepAction sleepAction;

    public DutchGoalBehavior(RoboudController controller, TaskFactory taskFactory) {
        super(controller, taskFactory);
        sleepAction = SleepActionPool.getInstance(controller).acquire(500);
        blocks.add(taskFactory.getDutchFlagLedTask());
        blocks.add(sleepAction);
        blocks.add(taskFactory.getDutchFlagLedTask());
    }

    @Override
    public void releaseActions() {
        SleepActionPool.getInstance(controller).release(sleepAction);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        if (currentBlock instanceof SleepAction && results.containsKey(sleepAction)) {
            Object info = results.get(sleepAction);
            if (info instanceof String) {
                String resultString = (String) info;
                if (resultString.toLowerCase().contains("awake")) {
                    return true;
                }
            }
        }
        return null;
    }
}
