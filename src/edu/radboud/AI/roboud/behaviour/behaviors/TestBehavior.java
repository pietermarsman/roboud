package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;

import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";


    public TestBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
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
// Tested and working:
// LEDAction
// RandomWander
// ShowTextAction
// SpeakAction
// ChoiceAction
// ConfirmationAction
// ExpressEmotionAction
// ListenAction
