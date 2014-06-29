package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.util.LedColor;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class TurnMeOffBehavior extends AbstractBehavior {

    public TurnMeOffBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        blocks.add(actionFactory.getLedAction(LedColor.YELLOW));
        blocks.add(actionFactory.getSpeakAction("To save my battery it is best to turn me off now"));
        blocks.add(actionFactory.getShowTextAction("See you soon, bye!"));
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
