package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class ShowTextAction extends AbstractAction {

    private String text;

    public ShowTextAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehavior) {
        addObserver(abstractBehavior);
        controller.showText(text);
        setChanged();
        notifyObservers();
    }
}
