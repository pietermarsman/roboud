package edu.radboud.ai.roboud.action;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class SpeakAction extends AbstractAction implements Observer {


    private final static String TAG = "SpeakAction";
    private String text;

    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    public SpeakAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    @Override
    public void doActions() {
        Log.i(TAG, "Going to speak: " + text);
        controller.speakText(this, text);
    }

    public String getText() {
        return text;
    }

    @Override
    public void update(Observable observable, Object data) {
        // Done with speaking
        setChanged();
        notifyObservers();
    }
}
