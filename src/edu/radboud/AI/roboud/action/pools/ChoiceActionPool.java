package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;

import java.util.List;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class ChoiceActionPool extends ActionPool<ChoiceAction> {

    private static ChoiceActionPool instance = null;

    private ChoiceActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized ChoiceActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new ChoiceActionPool(controller);
        return instance;
    }

    public ChoiceAction acquire(List<String> options, String question) {
        ChoiceAction choiceAction = acquire();
        choiceAction.setOptions(options);
        choiceAction.setQuestion(question);
        return choiceAction;
    }

    @Override
    protected ChoiceAction create() {
        return new ChoiceAction(controller);
    }
}
