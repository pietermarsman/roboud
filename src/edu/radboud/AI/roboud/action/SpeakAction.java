package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class SpeakAction extends AbstractAction implements TextToSpeech.OnInitListener {
    private TextToSpeech myTts;
    String text;

    public SpeakAction() {
        myTts = new TextToSpeech(null, this); //will null work? (used to be this)
    }

    @Override
    public void onInit(int status) {
        //super.onCreate(savedInstanceState);
        myTts = new TextToSpeech(null, this);
    }

    // I prefer this method, since a string can be passed
    public void speak(Scenario scenario, Observer abstractBehaviour, String text)
    {
        this.text = text;
        doActions(scenario,abstractBehaviour);
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        myTts.speak(text,TextToSpeech.QUEUE_FLUSH, null);
    }
}
