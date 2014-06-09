package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 *
 * DOES NOT WORK ON ANDROID!
 */
public class SpeakAction extends AbstractAction {

    private final static String TAG = "SpeakAction";
    private TextToSpeech myTts;
    private String text;

    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        //myTts = new TextToSpeech(controller, this); //will null work? (used to be this)
        this.text = text;
    }

    public SpeakAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehavior) {
        addObserver(abstractBehavior);
        //myTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        Log.i(TAG, "1st call controller.speakYo and say 'Hallo I'm testing this application for real'");
        controller.speakYo("Hallo I'm testing this application for real.");
        Log.i(TAG, "2nd call controller.speakYo and say 'Hallo I'm testing this application for real'");
        controller.speakYo("Hallo I'm testing this application for real.");
        setChanged();
        notifyObservers();
    }

}
