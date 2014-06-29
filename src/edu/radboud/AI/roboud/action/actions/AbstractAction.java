package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractAction extends BehaviorBlock {

    protected RoboudController controller;

    public AbstractAction(RoboudController controller) {
        this.controller = controller;
    }

}
