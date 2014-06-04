package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class ShowTextAction extends AbstractAction {

    String text;

    public ShowTextAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    @Override
    public void executeAction(Scenario scenario) {
        controller.showText(text);
    }
}
