package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.CombineAction;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.ShowTextAction;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
        ShowTextAction showTextAction = new ShowTextAction(controller, "Hello");
        ListenAction listenAction = new ListenAction(controller);
        CombineAction combineAction = new CombineAction(controller, showTextAction, listenAction);
        blocks.add(combineAction);
    }
}
