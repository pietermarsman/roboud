package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.LedAction;
import edu.radboud.ai.roboud.action.util.LedColor;

/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class LedActionPool extends ActionPool<LedAction> {

    private static LedActionPool instance = null;

    private LedActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized LedActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new LedActionPool(controller);
        return instance;
    }

    public LedAction acquire(LedColor color) {
        LedAction ledAction = acquire(); //retrieve LedAction
        ledAction.setColor(color); //set right color
        return ledAction; //return it
    }

    @Override
    protected LedAction create() {
        return new LedAction(controller);
    }
}
