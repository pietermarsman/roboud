package edu.radboud.ai.roboud.task.tasks;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.ListenAction;
import edu.radboud.ai.roboud.action.actions.SpeakAction;
import edu.radboud.ai.roboud.action.pools.ListenActionPool;
import edu.radboud.ai.roboud.action.pools.SpeakActionPool;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class OutloudAskQuestionTask extends AskQuestionTask {

    public OutloudAskQuestionTask(RoboudController controller, String question) {
        super(controller, question);
        actions.add(actionFactory.getSpeakAction(question));
        actions.add(actionFactory.getListenAction());
    }

    public OutloudAskQuestionTask(RoboudController controller, String[] questions) {
        super(controller);
        SpeakAction speakAction = actionFactory.getSpeakAction(questions);
        question = speakAction.getText();
        actions.add(speakAction);
        actions.add(actionFactory.getListenAction());
    }

    @Override
    protected Object processActionInformation() {
        return null;
    }

    @Override
    protected void processTaskInformation(Object information) {

    }
}
