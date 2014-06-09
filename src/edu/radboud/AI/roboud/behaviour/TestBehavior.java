package edu.radboud.ai.roboud.behaviour;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;

import edu.radboud.ai.roboud.action.ChoiceAction;
import edu.radboud.ai.roboud.action.SpeakAction;

import edu.radboud.ai.roboud.action.CombineAction;
import edu.radboud.ai.roboud.action.ListenAction;
import edu.radboud.ai.roboud.action.ShowTextAction;


import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
        SpeakAction speakAction = new SpeakAction(controller, "Hello");
        Log.i(TAG, "created new speakAction");
        blocks.add(speakAction);
        Log.i(TAG, "added speakAction to blocks");

        /*ShowTextAction showTextAction = new ShowTextAction(controller, "Hello");
        ListenAction listenAction = new ListenAction(controller);
        CombineAction combineAction = new CombineAction(controller, showTextAction, listenAction);
        blocks.add(combineAction);
        */
    }
}
