package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class SpeakAction extends AbstractAction implements TextToSpeech.OnInitListener {

    private String text;

    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    public SpeakAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    @Override
    public void onInit(int status) {
        // Probably nothing to do here
        // Test
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehavior) {
        addObserver(abstractBehavior);

        setChanged();
        notifyObservers();
    }
}
