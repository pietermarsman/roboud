package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.actions.CombineAction;

/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class CombineActionPool extends ActionPool<CombineAction> {

    private static CombineActionPool instance = null;

    private CombineActionPool(RoboudController controller){
        super(controller);
    }

    public static synchronized CombineActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new CombineActionPool(controller);
        return instance;
    }

    public CombineAction acquire(AbstractAction a, AbstractAction b){
        CombineAction combineAction = acquire();
        combineAction.setActions(a, b);
        return combineAction;
    }

    @Override
    protected CombineAction create() {
        return new CombineAction(controller);
    }
}
