package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.List;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class CompleModelBehavior extends AbstractBehavior {

    public CompleModelBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
    }

    @Override
    public List<BehaviorBlock> getBlocks() {
        return null;
    }
}
