package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class SpeakAction extends AbstractAction implements TextToSpeech.OnInitListener {

    String text;

    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    @Override
    public void onInit(int status) {
        // Probably nothing to do here
        // Test
    }

    @Override
    public void executeAction(Scenario scenario) {
        // TODO
    }
}
