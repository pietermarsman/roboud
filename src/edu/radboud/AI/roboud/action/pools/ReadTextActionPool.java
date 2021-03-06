package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ReadTextAction;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class ReadTextActionPool extends ActionPool<ReadTextAction> {

    private static ReadTextActionPool instance = null;

    private ReadTextActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized ReadTextActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ReadTextActionPool(controller);
        return instance;
    }

    public ReadTextAction acquire(String text) {
        ReadTextAction readTextAction = acquire();
        readTextAction.setText(text);
        return readTextAction;
    }

    public ReadTextAction acquire(String[] texts) {
        ReadTextAction readTextAction = acquire();
        readTextAction.setText(texts);
        return readTextAction;
    }

    @Override
    protected ReadTextAction create() {
        return new ReadTextAction(controller);
    }

}
