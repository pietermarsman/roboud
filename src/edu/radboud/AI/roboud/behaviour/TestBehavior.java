package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ChoiceAction;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
        List<String> options = new LinkedList<String>();
        options.add("Optie A");
        options.add("Optie B");
        options.add("Optie C");
        ChoiceAction ca = new ChoiceAction(controller, options);
        blocks.add(ca);
    }
}
