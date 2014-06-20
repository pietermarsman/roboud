package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
        //SpeakAction speakAction = new SpeakAction(controller, "Hello Yo mama is so fat see is");
        ListenAction action = new ListenAction(controller);
        blocks.add(action);


        /*ShowTextAction showTextAction = new ShowTextAction(controller, "Hello");
        ListenAction listenAction = new ListenAction(controller);
        CombineAction combineAction = new CombineAction(controller, showTextAction, listenAction);
        blocks.add(combineAction);
        */
    }
}
