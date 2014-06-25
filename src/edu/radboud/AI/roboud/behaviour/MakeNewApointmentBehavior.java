package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.List;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class MakeNewApointmentBehavior extends AbstractBehavior {

    public MakeNewApointmentBehavior(RoboudController controller, TaskFactory taskFactory) {
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
