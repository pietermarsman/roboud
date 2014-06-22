package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.LedAction;
import edu.radboud.ai.roboud.action.pools.LedActionPool;
import edu.radboud.ai.roboud.action.pools.SleepActionPool;
import edu.radboud.ai.roboud.action.util.LedColor;
import edu.radboud.ai.roboud.action.actions.SleepAction;

/**
 * Created by Mike Ligthart on 20-6-2014.
 */
public class DutchFlagLedTask extends AbstractTask {


    private SleepAction sleep;
    private LedAction red, white, blue;

    public DutchFlagLedTask(RoboudController controller) {
        super(controller);
        sleep = SleepActionPool.getInstance(controller).acquire(1000);
        red = LedActionPool.getInstance(controller).acquire(LedColor.RED);
        white = LedActionPool.getInstance(controller).acquire(LedColor.WHITE);
        blue = LedActionPool.getInstance(controller).acquire(LedColor.BLUE);

        actions.add(red);
        actions.add(sleep);
        actions.add(white);
        actions.add(sleep);
        actions.add(blue);
        actions.add(sleep);
    }

    @Override
    public void releaseActions() {
        SleepActionPool.getInstance(controller).release(sleep);
        LedActionPool.getInstance(controller).release(red);
        LedActionPool.getInstance(controller).release(white);
        LedActionPool.getInstance(controller).release(blue);
    }
}
