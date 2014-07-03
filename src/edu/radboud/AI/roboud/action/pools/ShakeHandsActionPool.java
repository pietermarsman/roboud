package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ShakeHandsAction;

/**
 * Created by mikel_000 on 3-7-2014.
 */
public class ShakeHandsActionPool extends ActionPool<ShakeHandsAction>{

    private static ShakeHandsActionPool instance = null;

    private ShakeHandsActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized ShakeHandsActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ShakeHandsActionPool(controller);
        return instance;
    }

    @Override
    protected ShakeHandsAction create() {
        return new ShakeHandsAction(controller);
    }
}
