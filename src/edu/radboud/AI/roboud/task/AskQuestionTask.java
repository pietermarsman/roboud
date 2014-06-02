package edu.radboud.ai.roboud.task;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class AskQuestionTask extends AbstractTask {

    public AskQuestionTask(String question) {
        // TODO
    }

    public AskQuestionTask(String[] questions) {
        this(SpeechRepertoire.randomChoice(questions));
    }
}
