package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.*;
import edu.radboud.ai.roboud.action.pools.ChoiceActionPool;
import edu.radboud.ai.roboud.action.pools.ConfirmationActionPool;
import edu.radboud.ai.roboud.action.pools.ShowTextActionPool;
import edu.radboud.ai.roboud.action.pools.SleepActionPool;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    //    private MotorAction forward, backward;
//    private SpeakAction speak;
    private ChoiceAction choiceAction;
    private ConfirmationAction confirmationAction;
    private ExpressEmotionAction expression;
    private ShowTextAction showTextAction;
    private SleepAction sleepAction;

    public TestBehavior(RoboudController controller, TaskFactory taskFactory) {
        super(controller, taskFactory);
//        blocks.add(taskFactory.getDutchFlagLedTask());
//        forward = MotorActionPool.getInstance(controller).acquire(RobotDirection.FORWARD, RobotSpeed.NORMAL);
//        backward = MotorActionPool.getInstance(controller).acquire(RobotDirection.BACKWARD, RobotSpeed.NORMAL);
//        speak = SpeakActionPool.getInstance(controller).acquire(SpeechRepertoire.textIntroduceMyself);
//        blocks.add(forward);
//        blocks.add(speak);
//        blocks.add(backward);
//        expression = ExpressEmotionActionPool.getInstance(controller).acquire(FaceExpression.SAD);
//        blocks.add(expression);
        List<String> options = new LinkedList<String>();
        options.add("Optie 1");
        options.add("Optie 2");
        choiceAction = ChoiceActionPool.getInstance(controller).acquire(options);
        confirmationAction = ConfirmationActionPool.getInstance(controller).acquire("Are you really really sure?");
        showTextAction = ShowTextActionPool.getInstance(controller).acquire();
        sleepAction = SleepActionPool.getInstance(controller).acquire(5000);
        blocks.add(choiceAction);
        blocks.add(confirmationAction);
        blocks.add(showTextAction);
        blocks.add(sleepAction);
    }

    @Override
    public void releaseActions() {
//        MotorActionPool.getInstance(controller).release(forward);
//        MotorActionPool.getInstance(controller).release(backward);
//        SpeakActionPool.getInstance(controller).release(speak);
//        ExpressEmotionActionPool.getInstance(controller).release(expression);
        ChoiceActionPool.getInstance(controller).release(choiceAction);
        ConfirmationActionPool.getInstance(controller).release(confirmationAction);
        ShowTextActionPool.getInstance(controller).release(showTextAction);
        SleepActionPool.getInstance(controller).release(sleepAction);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        if (currentBlock.getClass().equals(ConfirmationAction.class)) {
            ConfirmationAction confirmationAction1 = (ConfirmationAction) currentBlock;
            return String.valueOf(confirmationAction1.getResult());
        }
        return null;
    }
}
// Tested and working:
// LEDAction
// RandomWander
// ShowTextAction
