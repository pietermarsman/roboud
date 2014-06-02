package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.task.AbstractTask;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class SpeakTask extends AbstractTask {

    public SpeakTask(String text) {

    }

    public SpeakTask(String[] text) {
        this(SpeechRepertoire.randomChoice(text));
    }
}
