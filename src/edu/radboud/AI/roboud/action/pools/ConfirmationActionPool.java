package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ConfirmationAction;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class ConfirmationActionPool extends ActionPool<ConfirmationAction> {

    private static ConfirmationActionPool instance = null;

    private ConfirmationActionPool(RoboudController controller){
        super(controller);
    }

    public static synchronized ConfirmationActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ConfirmationActionPool(controller);
        return instance;
    }

    public ConfirmationAction acquire(String question){
        ConfirmationAction confirmationAction = acquire();
        confirmationAction.setQuestion(question);
        return confirmationAction;
    }

    @Override
    protected ConfirmationAction create() {
        return new ConfirmationAction(controller);
    }
}
