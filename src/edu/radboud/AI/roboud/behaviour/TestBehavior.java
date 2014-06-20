package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.SpeakAction;
import edu.radboud.ai.roboud.task.SpeechRepertoire;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

<<<<<<<HEAD
        =======
        >>>>>>>7b32138d445ff27141faa06946bd706d357a2cd0

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);
        <<<<<<<HEAD
        blocks.add(taskFactory.getDutchFlagLedTask());
        =======
        //SpeakAction speakAction = new SpeakAction(controller, "Hello Yo mama is so fat see is");
        SpeakAction speakAction = new SpeakAction(controller, SpeechRepertoire.textIntroduceMyself);
        blocks.add(speakAction);


        /*ShowTextAction showTextAction = new ShowTextAction(controller, "Hello");
        ListenAction listenAction = new ListenAction(controller);
        CombineAction combineAction = new CombineAction(controller, showTextAction, listenAction);
        blocks.add(combineAction);
        */
        >>>>>>>7 b32138d445ff27141faa06946bd706d357a2cd0
    }
}
