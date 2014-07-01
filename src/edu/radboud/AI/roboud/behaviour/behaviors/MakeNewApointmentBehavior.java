package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class MakeNewApointmentBehavior extends AbstractBehavior {

    public MakeNewApointmentBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
    }

    @Override
    protected Object processInformation(AbstractAction currentAction) {
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
