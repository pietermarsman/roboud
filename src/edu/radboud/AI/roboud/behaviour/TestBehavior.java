package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

//    SpeakAction speakAction1, speakAction2, speakAction3;
//    ReadTextAction readTextAction;
//    ListenAction listenAction1;
//    ShowTextAction showTextAction;

    public TestBehavior(RoboudController controller, TaskFactory taskFactory) {
        super(controller, taskFactory);
//        speakAction1 = SpeakActionPool.getInstance(controller).acquire("Talk to me goose");
//        listenAction1 = ListenActionPool.getInstance(controller).acquire();
//        speakAction2 = SpeakActionPool.getInstance(controller).acquire("Ouch. Not so loud. I am right next to you");
//        showTextAction = ShowTextActionPool.getInstance(controller).acquire();
//        readTextAction = ReadTextActionPool.getInstance(controller).acquire();
//        speakAction3 = SpeakActionPool.getInstance(controller).acquire();
//        blocks.add(speakAction1);
//        blocks.add(listenAction1);
//        blocks.add(speakAction2);
//        blocks.add(showTextAction);
//        blocks.add(readTextAction);
//        blocks.add(speakAction3);
    }

    @Override
    public void releaseActions() {
//        SpeakActionPool.getInstance(controller).release(speakAction1);
//        SpeakActionPool.getInstance(controller).release(speakAction2);
//        SpeakActionPool.getInstance(controller).release(speakAction3);
//        ReadTextActionPool.getInstance(controller).release(readTextAction);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
//        if (currentBlock == speakAction2)
//            return ((List<String>) results.get(listenAction1)).get(0);
//        if (currentBlock == readTextAction)
//            return readTextAction.getInformation();
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
