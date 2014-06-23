package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ListenAction;
import edu.radboud.ai.roboud.action.actions.SpeakAction;
import edu.radboud.ai.roboud.action.pools.ListenActionPool;
import edu.radboud.ai.roboud.action.pools.SpeakActionPool;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class OutloudAskQuestionTask extends AskQuestionTask {

    private SpeakAction speakAction;
    private ListenAction listenAction;

    public OutloudAskQuestionTask(RoboudController controller, String question){
        super(controller, question);
        speakAction = SpeakActionPool.getInstance(controller).acquire(question);
        listenAction = ListenActionPool.getInstance(controller).acquire();
        actions.add(speakAction);
        actions.add(listenAction);
    }

    public OutloudAskQuestionTask(RoboudController controller, String[] questions){
        super(controller);
        speakAction = SpeakActionPool.getInstance(controller).acquire(questions);
        question = speakAction.getText();
        listenAction = ListenActionPool.getInstance(controller).acquire();
        actions.add(speakAction);
        actions.add(listenAction);
    }

    @Override
    public void releaseActions() {
        SpeakActionPool.getInstance(controller).release(speakAction);
        ListenActionPool.getInstance(controller).release(listenAction);
    }

    @Override
    protected Object processInformation() {
        return null;
    }
}
