package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ExpressEmotionAction;
import edu.radboud.ai.roboud.action.pools.ExpressEmotionActionPool;
import edu.radboud.ai.roboud.action.util.FaceExpression;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    //    private MotorAction forward, backward;
//    private SpeakAction speak;
    private ExpressEmotionAction expression;

    public TestBehavior(RoboudController controller, TaskFactory taskFactory) {
        super(controller, taskFactory);
//        blocks.add(taskFactory.getDutchFlagLedTask());
//        forward = MotorActionPool.getInstance(controller).acquire(RobotDirection.FORWARD, RobotSpeed.NORMAL);
//        backward = MotorActionPool.getInstance(controller).acquire(RobotDirection.BACKWARD, RobotSpeed.NORMAL);
//        speak = SpeakActionPool.getInstance(controller).acquire(SpeechRepertoire.textIntroduceMyself);
//        blocks.add(forward);
//        blocks.add(speak);
//        blocks.add(backward);

        expression = ExpressEmotionActionPool.getInstance(controller).acquire(FaceExpression.SAD);
        blocks.add(expression);
    }

    @Override
    public void releaseActions() {
//        MotorActionPool.getInstance(controller).release(forward);
//        MotorActionPool.getInstance(controller).release(backward);
//        SpeakActionPool.getInstance(controller).release(speak);

        ExpressEmotionActionPool.getInstance(controller).release(expression);

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
