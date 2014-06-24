package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ReadTextAction;
import edu.radboud.ai.roboud.action.actions.ShowTextAction;
import edu.radboud.ai.roboud.action.pools.ReadTextActionPool;
import edu.radboud.ai.roboud.action.pools.ShowTextActionPool;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class ScreenAskQuestionTask extends AskQuestionTask {

    private ShowTextAction showTextAction;
    private ReadTextAction readTextAction;

    public ScreenAskQuestionTask(RoboudController controller, String question) {
        super(controller, question);
        showTextAction = ShowTextActionPool.getInstance(controller).acquire(question);
        readTextAction = ReadTextActionPool.getInstance(controller).acquire();
        actions.add(showTextAction);
        actions.add(readTextAction);
    }

    public ScreenAskQuestionTask(RoboudController controller, String[] questions) {
        super(controller);
        showTextAction = ShowTextActionPool.getInstance(controller).acquire(question);
        question = showTextAction.getText();
        readTextAction = ReadTextActionPool.getInstance(controller).acquire();
        actions.add(showTextAction);
        actions.add(readTextAction);
    }

    @Override
    public void releaseActions() {
        ShowTextActionPool.getInstance(controller).release(showTextAction);
        ReadTextActionPool.getInstance(controller).release(readTextAction);
    }
}
