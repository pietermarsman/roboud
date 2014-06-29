package edu.radboud.ai.roboud.task.tasks;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.LedAction;
import edu.radboud.ai.roboud.action.actions.SleepAction;
import edu.radboud.ai.roboud.action.pools.LedActionPool;
import edu.radboud.ai.roboud.action.pools.SleepActionPool;
import edu.radboud.ai.roboud.action.util.LedColor;

/**
 * Created by Mike Ligthart on 20-6-2014.
 */
public class DutchFlagLedTask extends AbstractTask {


    public DutchFlagLedTask(RoboudController controller) {
        super(controller);
        actions.add(actionFactory.getLedAction(LedColor.RED));
        actions.add(actionFactory.getSleepAction(1000));
        actions.add(actionFactory.getLedAction(LedColor.WHITE));
        actions.add(actionFactory.getSleepAction(1000));
        actions.add(actionFactory.getLedAction(LedColor.BLUE));
        actions.add(actionFactory.getSleepAction(1000));
    }

    @Override
    protected Object processActionInformation() {
        return null;
    }

    @Override
    protected void processTaskInformation(Object information) {
    }
}
