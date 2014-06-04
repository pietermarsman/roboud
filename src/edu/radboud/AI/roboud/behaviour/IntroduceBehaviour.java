package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.AskQuestionTask;
import edu.radboud.ai.roboud.task.SpeakTask;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehaviour extends AbstractBehaviour {

    public IntroduceBehaviour(RoboudController controller) {
        super(controller);
        blocks.add(new SpeakTask(SpeechRepertoire.textGreetingStart));
        blocks.add(new AskQuestionTask(controller, SpeechRepertoire.questionName));
        blocks.add(new SpeakTask(SpeechRepertoire.textIntroduceMyself));
        blocks.add(new AskQuestionTask(controller, SpeechRepertoire.questionAge));
        blocks.add(new AskQuestionTask(controller, SpeechRepertoire.questionSex));
        blocks.add(new SpeakTask("Oké, now I know enough about you"));
        blocks.add(new SpeakTask(SpeechRepertoire.textGreetingEnd));
    }
}
