package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class SpeakAction extends AbstractAction implements TextToSpeech.OnInitListener {

    String text;

    public SpeakAction(String text) {
        this.text = text;
    }

    @Override
    public void onInit(int status) {
        // Probably nothing to do here
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        // TODO
    }
}
