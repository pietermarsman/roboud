package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractAction extends Observable implements Action {

    protected RoboudController controller;

    public AbstractAction(RoboudController controller) {
        this.controller = controller;
    }

}
