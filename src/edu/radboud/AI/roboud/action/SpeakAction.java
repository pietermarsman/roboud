package edu.radboud.ai.roboud.action;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.task.SpeechRepertoire;

import java.util.Locale;
import java.util.Observer;

import static android.speech.tts.TextToSpeech.OnInitListener;

/**
 * Created by Pieter Marsman on 27-5-2014.
 *
 */
public class SpeakAction extends AbstractAction implements OnInitListener{

    private final static String TAG = "SpeakAction";
    private TextToSpeech myTTS;
    private String text;

    public SpeakAction(RoboudController controller, String text) {
        super(controller);
        myTTS = new TextToSpeech(controller, this);
        this.text = text;
    }

    public SpeakAction(RoboudController controller, String[] texts) {
        this(controller, SpeechRepertoire.randomChoice(texts));
    }

    @Override
    public void onInit(int initStatus) {
        // check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE){
                myTTS.setLanguage(Locale.US);
            }
        } else if (initStatus == TextToSpeech.ERROR) {
            Log.w(TAG, "TTS: failed to initialize ");
        }
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehavior) {
        addObserver(abstractBehavior);
        Log.i(TAG, "Going to speak: " + text);
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        notifyWhenReadyWithSpeaking();
    }

    private void notifyWhenReadyWithSpeaking(){
    //Not the best way to do this probably
        Runnable checker = new Runnable() {
            public void run(){
                while(myTTS.isSpeaking()){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Something went wrong in the speak checker", e);
                    }
                }
                setChanged();
                notifyObservers();
            }
        };

        new Thread(checker).start();
    }

}
