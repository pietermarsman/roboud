package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.ShowTextAction;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehaviour extends AbstractBehaviour {

    public TestBehaviour(RoboudController controller) {
        super(controller);
        blocks.add(new ShowTextAction(controller, "Hello there. I am a behavior."));
        blocks.add(new ListenAction(controller));
    }
}
