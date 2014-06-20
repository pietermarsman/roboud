package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.LedAction;
import edu.radboud.ai.roboud.action.LedColor;
import edu.radboud.ai.roboud.action.SleepAction;

/**
 * Created by Mike Ligthart on 20-6-2014.
 */
public class DutchFlagLedTask extends AbstractTask {


    public DutchFlagLedTask(RoboudController controller) {
        super();
        SleepAction sleep = new SleepAction(controller, 1000);
        actions.add(new LedAction(controller, LedColor.RED));
        actions.add(sleep);
        actions.add(new LedAction(controller, LedColor.WHITE));
        actions.add(sleep);
        actions.add(new LedAction(controller, LedColor.BLUE));
        actions.add(sleep);
    }
}
