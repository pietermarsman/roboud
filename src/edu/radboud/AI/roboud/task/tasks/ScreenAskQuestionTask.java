package edu.radboud.ai.roboud.task.tasks;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.ReadTextAction;
import edu.radboud.ai.roboud.action.actions.ShowTextAction;
import edu.radboud.ai.roboud.action.pools.ReadTextActionPool;
import edu.radboud.ai.roboud.action.pools.ShowTextActionPool;

/**
 * Created by mikel_000 on 22-6-2014.
 */
public class ScreenAskQuestionTask extends AskQuestionTask {


    public ScreenAskQuestionTask(RoboudController controller, String question) {
        super(controller, question);
        actions.add(actionFactory.getShowTextAction(question));
        actions.add(actionFactory.getReadTextAction());
    }

    public ScreenAskQuestionTask(RoboudController controller, String[] questions) {
        super(controller);
        ShowTextAction showTextAction = actionFactory.getShowTextAction(questions);
        question = showTextAction.getText();
        actions.add(showTextAction);
        actions.add(actionFactory.getReadTextAction());
    }

    @Override
    protected Object processActionInformation() {
        return null;
    }

    @Override
    protected void processTaskInformation(Object information) {

    }
}
