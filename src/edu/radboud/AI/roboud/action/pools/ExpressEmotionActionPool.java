package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ExpressEmotionAction;
import edu.radboud.ai.roboud.action.util.FaceExpression;

/**
 * Created by mikel_000 on 25-6-2014.
 */
public class ExpressEmotionActionPool extends ActionPool<ExpressEmotionAction> {

    private static ExpressEmotionActionPool instance = null;

    private ExpressEmotionActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized ExpressEmotionActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ExpressEmotionActionPool(controller);
        return instance;
    }

    public ExpressEmotionAction acquire(FaceExpression expression) {
        ExpressEmotionAction expressEmotionAction = acquire(); //retrieve LedAction
        expressEmotionAction.setExpression(expression); //set right color
        return expressEmotionAction; //return it
    }

    @Override
    protected ExpressEmotionAction create() {
        return new ExpressEmotionAction(controller);
    }
}