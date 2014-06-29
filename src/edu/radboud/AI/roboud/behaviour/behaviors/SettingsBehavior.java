package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class SettingsBehavior extends AbstractBehavior {

    public SettingsBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        blocks.add(actionFactory.getConfirmationAction("Can I drive?"));
        blocks.add(actionFactory.getConfirmationAction("Can I turn?"));
    }


    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
