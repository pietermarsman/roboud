package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.SpeakAction;
import edu.radboud.ai.roboud.action.pools.SpeakActionPool;
import edu.radboud.ai.roboud.task.SpeechRepertoire;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehavior extends AbstractBehavior {

    private SpeakAction start, introduce, knowEnough, end;

    public IntroduceBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);

        start = SpeakActionPool.getInstance(controller).acquire(SpeechRepertoire.textGreetingStart);
        introduce = SpeakActionPool.getInstance(controller).acquire(SpeechRepertoire.textIntroduceMyself);
        knowEnough = SpeakActionPool.getInstance(controller).acquire("Oké, now I know enough about you");
        end = SpeakActionPool.getInstance(controller).acquire(SpeechRepertoire.textGreetingEnd);
        blocks.add(start);
        blocks.add(taskFactory.getAskQuestionTask(SpeechRepertoire.textIntroduceMyself));
        blocks.add(introduce);
        blocks.add(taskFactory.getAskQuestionTask(SpeechRepertoire.questionAge));
        blocks.add(taskFactory.getAskQuestionTask(SpeechRepertoire.questionSex));
        blocks.add(knowEnough);
        blocks.add(end);
    }

    @Override
    public void releaseActions() {
        SpeakActionPool.getInstance(controller).release(start);
        SpeakActionPool.getInstance(controller).release(introduce);
        SpeakActionPool.getInstance(controller).release(knowEnough);
        SpeakActionPool.getInstance(controller).release(end);
    }

    @Override
    protected Object processInformation() {
        return null;
    }
}
