package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class SpeakAction implements TextToSpeech.OnInitListener, Action {

    String text;

    public SpeakAction(String text) {
        this.text = text;
    }

    @Override
    public void onInit(int status) {

    }

    @Override
    public void doActions(Scenario scenario) {

    }

    @Override
    public boolean isFinished() {

    }
}
