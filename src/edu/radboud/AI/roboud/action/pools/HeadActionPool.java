package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.HeadAction;
import edu.radboud.ai.roboud.action.util.HeadDirection;

/**
 * Created by Guido on 02-07-14.
 */
public class HeadActionPool extends ActionPool<HeadAction>{

    private static HeadActionPool instance = null;

    private HeadActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized HeadActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new HeadActionPool(controller);
        return instance;
    }

    public HeadAction acquire(HeadDirection headDirection) {
        HeadAction headAction = acquire();
        headAction.setHeadDirection(headDirection);
        return headAction;
    }

    @Override
    protected HeadAction create() {
        return new HeadAction(controller);
    }
}


