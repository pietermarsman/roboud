package edu.radboud.ai.roboud.behaviour.behaviors;

import android.app.Notification;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.List;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AssignmentBehavior extends AbstractBehavior {

    public AssignmentBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
    }

    @Override
    public List<BehaviorBlock> getBlocks() {
        return null;
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
