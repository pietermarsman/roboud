package edu.radboud.ai.roboud.action.behaviour;

import edu.radboud.ai.roboud.task.SpeakTask;
import edu.radboud.ai.roboud.task.AskQuestionTask;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class IntroduceBehaviour extends AbstractBehaviour {

    public IntroduceBehaviour() {
        blocks.add(new SpeakTask(SpeechRepertoire.textGreetingStart));
        blocks.add(new AskQuestionTask(SpeechRepertoire.questionName));
        blocks.add(new SpeakTask(SpeechRepertoire.textIntroduceMyself));
        blocks.add(new AskQuestionTask(SpeechRepertoire.questionAge));
        blocks.add(new AskQuestionTask(SpeechRepertoire.questionSex));
        blocks.add(new SpeakTask("Oké, now I know enough about you"));
        blocks.add(new SpeakTask(SpeechRepertoire.textGreetingEnd));
    }
}
