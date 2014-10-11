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

    //A singleton pattern is applied here
    public ChoiceAction acquire(List<String> options, String question) {
        ChoiceAction choiceAction = acquire(); //Acquire empty choice action
        choiceAction.setOptions(options); //Set the options
        choiceAction.setQuestion(question);//Set the question
        return choiceAction;//Return the build action
    }

    @Override
    protected ChoiceAction create() {
        return new ChoiceAction(controller);
    }
}
