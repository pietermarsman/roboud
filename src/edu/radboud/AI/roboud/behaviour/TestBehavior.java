package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.SpeakAction;
import edu.radboud.ai.roboud.task.SpeechRepertoire;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
        blocks.add(taskFactory.getDutchFlagLedTask());
        //blocks.add(new MotorAction(controller, RobotDirection.FORWARD, RobotSpeed.NORMAL));
        //blocks.add(new SpeakAction(controller, SpeechRepertoire.textIntroduceMyself));
        //blocks.add(new MotorAction(controller, RobotDirection.BACKWARD, RobotSpeed.NORMAL));
    }

    @Override
    public void releaseActions() {
        //Only necessary when actions are being used.
    }
}
