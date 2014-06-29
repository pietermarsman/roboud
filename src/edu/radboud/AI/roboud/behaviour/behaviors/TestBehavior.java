package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.ListenAction;
import edu.radboud.ai.roboud.action.actions.ReadTextAction;
import edu.radboud.ai.roboud.action.actions.ShowTextAction;
import edu.radboud.ai.roboud.action.actions.SpeakAction;
import edu.radboud.ai.roboud.action.pools.ListenActionPool;
import edu.radboud.ai.roboud.action.pools.ReadTextActionPool;
import edu.radboud.ai.roboud.action.pools.ShowTextActionPool;
import edu.radboud.ai.roboud.action.pools.SpeakActionPool;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.List;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    SpeakAction speakAction1, speakAction2, speakAction3;
    ReadTextAction readTextAction;
    ListenAction listenAction1;
    ShowTextAction showTextAction;

    public TestBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        speakAction1 = actionFactory.getSpeakAction("Talk to me goose");
        listenAction1 = actionFactory.getListenAction();
        speakAction2 = actionFactory.getSpeakAction("Ouch. Not so loud. I am right next to you");
        showTextAction = actionFactory.getShowTextAction(" ");
        readTextAction = actionFactory.getReadTextAction();
        speakAction3 = actionFactory.getSpeakAction(" ");
        blocks.add(speakAction1);
        blocks.add(listenAction1);
        blocks.add(speakAction2);
        blocks.add(showTextAction);
        blocks.add(readTextAction);
        blocks.add(speakAction3);
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        if (currentBlock == speakAction2)
            return ((List<String>) results.get(listenAction1)).get(0);
        if (currentBlock == readTextAction)
            return readTextAction.getInformation();
        return null;
    }

    @Override
    public Object getInformation() {
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
