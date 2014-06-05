package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ChoiceAction;
import edu.radboud.ai.roboud.action.ShowTextAction;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehaviour extends AbstractBehaviour {

    public TestBehaviour(RoboudController controller) {
        super(controller);
        List<String> options = new LinkedList<String>();
        options.add("Optie A");
        options.add("Optie B");
        options.add("Optie C");
        ChoiceAction ca = new ChoiceAction(controller, options);
        blocks.add(ca);
        blocks.add(new ShowTextAction(controller, "Result: " + ca.getResultString()));
    }
}
