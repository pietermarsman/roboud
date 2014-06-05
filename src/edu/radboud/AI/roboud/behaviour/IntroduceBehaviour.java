package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.SpeakAction;
import edu.radboud.ai.roboud.task.AskQuestionTask;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehaviour extends AbstractBehaviour {

    public IntroduceBehaviour(RoboudController controller) {
        super(controller);
        blocks.add(new SpeakAction(controller, SpeechRepertoire.textGreetingStart));
        blocks.add(new AskQuestionTask(controller, SpeechRepertoire.questionName));
        blocks.add(new SpeakAction(controller, SpeechRepertoire.textIntroduceMyself));
        blocks.add(new AskQuestionTask(controller, SpeechRepertoire.questionAge));
        blocks.add(new AskQuestionTask(controller, SpeechRepertoire.questionSex));
        blocks.add(new SpeakAction(controller, "Oké, now I know enough about you"));
        blocks.add(new SpeakAction(controller, SpeechRepertoire.textGreetingEnd));
    }
}
