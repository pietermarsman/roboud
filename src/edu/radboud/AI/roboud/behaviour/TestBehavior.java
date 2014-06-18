package edu.radboud.ai.roboud.behaviour;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;

import edu.radboud.ai.roboud.action.ChoiceAction;
import edu.radboud.ai.roboud.action.SpeakAction;

import edu.radboud.ai.roboud.action.CombineAction;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.ShowTextAction;
import edu.radboud.ai.roboud.task.AbstractTask;
import edu.radboud.ai.roboud.task.TaskFactory;


import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
        //SpeakAction speakAction = new SpeakAction(controller, "Hello Yo mama is so fat see is");
        AbstractTask askquestion = TaskFactory.getInstance().getAskQuestionTask("Hello how are you doing today?");
        blocks.add(askquestion);


        /*ShowTextAction showTextAction = new ShowTextAction(controller, "Hello");
        ListenAction listenAction = new ListenAction(controller);
        CombineAction combineAction = new CombineAction(controller, showTextAction, listenAction);
        blocks.add(combineAction);
        */
    }
}
