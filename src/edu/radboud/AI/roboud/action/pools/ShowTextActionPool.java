package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ShowTextAction;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class ShowTextActionPool extends ActionPool<ShowTextAction> {

    private static ShowTextActionPool instance = null;

    private ShowTextActionPool(RoboudController controller){
        super(controller);
    }

    public static synchronized ShowTextActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ShowTextActionPool(controller);
        return instance;
    }

    public ShowTextAction acquire(String text){
        ShowTextAction showTextAction = acquire();
        showTextAction.setText(text);
        return showTextAction;
    }

    public ShowTextAction acquire(String[] texts){
        ShowTextAction showTextAction = acquire();
        showTextAction.setText(texts);
        return showTextAction;
    }

    @Override
    protected ShowTextAction create() {
        return new ShowTextAction(controller);
    }
}
