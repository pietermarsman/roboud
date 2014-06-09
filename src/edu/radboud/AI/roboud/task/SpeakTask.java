package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class SpeakTask extends AbstractTask {

    public SpeakTask(String text) {
        // TODO
    }

    public SpeakTask(String[] text) {
        this(SpeechRepertoire.randomChoice(text));
    }

    @Override
    public boolean isSuitable(Scenario scenario) {
        // TODO
        return false;
    }
}
