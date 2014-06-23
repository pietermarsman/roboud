package edu.radboud.ai.roboud.action.actions;

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

    @Deprecated
    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        this.text = text;
    }

    @Deprecated
    public SpeakAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    public SpeakAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(){
        if(text == null){
            throw new NullPointerException("text cannot be null");
        }
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

    public void setText(String text){
        this.text = text;
    }

    public void setText(String[] texts){
        this.text = SpeechRepertoire.randomChoice(texts);
    }
}