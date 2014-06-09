package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractAction extends Observable implements Action {

    protected RoboudController controller;

    public AbstractAction(RoboudController controller) {
        this.controller = controller;
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        this.addObserver(abstractBehaviour);
        this.executeAction(scenario);
    }

    /**
     * Should be called when an action is done. Makes sure that the Behavior/CombineAction is notified that this Action
     * is done.
     */
    public void actionDone() {
        setChanged();
        notifyObservers();
    }
}
