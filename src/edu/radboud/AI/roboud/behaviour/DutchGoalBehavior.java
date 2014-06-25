package edu.radboud.ai.roboud.behaviour;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ListenAction;
import edu.radboud.ai.roboud.action.pools.ListenActionPool;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by mikel_000 on 25-6-2014.
 */
public class DutchGoalBehavior extends AbstractBehavior {

    public final static String TAG = "DutchGoalBehavior";
    private ListenAction listenForGoal;

    public DutchGoalBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
        listenForGoal = ListenActionPool.getInstance(controller).acquire();
        blocks.add(taskFactory.getDutchFlagLedTask());
        blocks.add(listenForGoal);
        blocks.add(taskFactory.getDutchFlagLedTask());
    }

    @Override
    public void releaseActions() {
        ListenActionPool.getInstance(controller).release(listenForGoal);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        Log.i(TAG, "currentBlock simple name is " + currentBlock.getClass().getSimpleName());
        if (currentBlock instanceof ListenAction) {
            Object info = results.get(listenForGoal);
            if (info instanceof String) {
                String resultString = (String) info;
                Log.i(TAG, resultString);
                if (resultString.toLowerCase().contains("goal")) {
                    return true;
                }
            }
        }
        return null;
    }
}
