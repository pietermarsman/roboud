package edu.radboud.ai.roboud.behaviour.behaviors;

import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.util.SpeechRepertoire;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehavior extends AbstractBehavior {

    public IntroduceBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        super(actionFactory, taskFactory);
        blocks.add(actionFactory.getSpeakAction(SpeechRepertoire.textGreetingStart));
        blocks.add(taskFactory.getAskQuestionTask(SpeechRepertoire.textIntroduceMyself));
        blocks.add(actionFactory.getSpeakAction(SpeechRepertoire.textIntroduceMyself));
        blocks.add(taskFactory.getAskQuestionTask(SpeechRepertoire.questionAge));
        blocks.add(taskFactory.getAskQuestionTask(SpeechRepertoire.questionSex));
        blocks.add(actionFactory.getSpeakAction("Oké, now I know enough about you"));
        blocks.add(actionFactory.getSpeakAction(SpeechRepertoire.textGreetingEnd));
    }

    @Override
    protected Object processInformation(BehaviorBlock currentBlock) {
        return null;
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
