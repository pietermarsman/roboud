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

    private TextToSpeech myTts;
    private String text;

    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        myTts = new TextToSpeech(null, this); //will null work? (used to be this)
        this.text = text;
    }

    public SpeakAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    @Override
    public void onInit(int status) {
        //super.onCreate(savedInstanceState);
        myTts = new TextToSpeech(null, this);
    }

    // I prefer this method, since a string can be passed
    public void speak(Scenario scenario, Observer abstractBehaviour, String text) {
        this.text = text;
        doActions(scenario, abstractBehaviour);
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehavior) {
        addObserver(abstractBehavior);
        myTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        setChanged();
        notifyObservers();
    }

}
