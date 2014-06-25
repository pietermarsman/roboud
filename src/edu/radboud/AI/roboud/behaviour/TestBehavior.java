package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ChoiceAction;
import edu.radboud.ai.roboud.action.actions.ExpressEmotionAction;
import edu.radboud.ai.roboud.action.pools.ChoiceActionPool;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";
    //    private MotorAction forward, backward;
//    private SpeakAction speak;
    private ChoiceAction choiceAction;
    private ExpressEmotionAction expression;

    public TestBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
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
        blocks.add(choiceAction);
    }

    @Override
    public void releaseActions() {
//        MotorActionPool.getInstance(controller).release(forward);
//        MotorActionPool.getInstance(controller).release(backward);
//        SpeakActionPool.getInstance(controller).release(speak);
//        ExpressEmotionActionPool.getInstance(controller).release(expression);
        ChoiceActionPool.getInstance(controller).release(choiceAction);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        return null;
    }
}
// Tested and working:
// LEDAction
// RandomWander
// ShowTextAction
