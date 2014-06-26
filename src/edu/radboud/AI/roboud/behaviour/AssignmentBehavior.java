package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.List;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AssignmentBehavior extends AbstractBehavior {

    public AssignmentBehavior(RoboudController controller, TaskFactory taskFactory) {
        super(controller, taskFactory);
    }

    @Override
    public List<BehaviorBlock> getBlocks() {
        return null;
    }

    @Override
    public void releaseActions() {
        //TODO
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        return null;
    }
}
