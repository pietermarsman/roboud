package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.LedAction;
import edu.radboud.ai.roboud.action.LedColor;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
        //SpeakAction speakAction = new SpeakAction(controller, "Hello Yo mama is so fat see is");
        LedAction ledAction = new LedAction(controller, LedColor.random());
        blocks.add(ledAction);


        /*ShowTextAction showTextAction = new ShowTextAction(controller, "Hello");
        ListenAction listenAction = new ListenAction(controller);
        CombineAction combineAction = new CombineAction(controller, showTextAction, listenAction);
        blocks.add(combineAction);
        */
    }
}
