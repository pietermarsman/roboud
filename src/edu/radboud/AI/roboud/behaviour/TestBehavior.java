package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ConfirmationAction;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
        ConfirmationAction confirmationAction = new ConfirmationAction(controller, "Are you really, really sure?");
        blocks.add(confirmationAction);
    }
}
