package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SettingsBehavior extends AbstractBehavior {

    public SettingsBehavior(ActionFactory actionFactory, Scenario scenario) {
        super(actionFactory, scenario);
        actions.add(actionFactory.getConfirmationAction("Can I drive?"));
        actions.add(actionFactory.getConfirmationAction("Can I turn?"));
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
