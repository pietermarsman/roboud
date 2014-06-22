package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;
import edu.radboud.ai.roboud.action.actions.ListenAction;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class ListenActionPool extends ActionPool<ListenAction> {

    private static ListenActionPool instance = null;

    private ListenActionPool(RoboudController controller){
        super(controller);
    }

    public static synchronized ListenActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ListenActionPool(controller);
        return instance;
    }

    @Override
    protected ListenAction create() {
        return new ListenAction(controller);
    }

}
