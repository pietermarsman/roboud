package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractAction extends Observable {

    protected RoboudController controller;

    public AbstractAction(RoboudController controller) {
        this.controller = controller;
    }

    public abstract void doActions(Object information);

    public abstract Object getInformation();

}
