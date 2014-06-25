package edu.radboud.ai.roboud.senses;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

import java.util.Locale;
import java.util.Observable;

/**
 * Created by Pieter Marsman on 20-6-2014.
 */
public class SpeechEngine extends Observable implements TextToSpeech.OnInitListener, ActivityResultProcessor {
    private static final String TAG = "SpeechEngine";
    private static final int MY_DATA_CHECK_CODE = 30, INSTALL_CODE = 31;
    private static final Locale LANGUAGE = Locale.US;
    private TextToSpeech myTTS;
    private RoboudController controller;
    private boolean available;

    public SpeechEngine(RoboudController controller) {
        this.controller = controller;
        myTTS = new TextToSpeech(controller, this);

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        controller.startNewActivityForResult(checkIntent, MY_DATA_CHECK_CODE, this);
    }

    @Override
    public void onInit(int initStatus) {
        // check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS != null)
                myTTS.setLanguage(LANGUAGE);
            else
                Log.w(TAG, "Trying to set language, but in the meantime the TextToSpeech variable was destroyed");
        } else if (initStatus == TextToSpeech.ERROR) {
            Log.w(TAG, "TTS: failed to initialize ");
        }
    }

    private void notifyWhenReadyWithSpeaking() {
        //Not the best way to do this probably
        Runnable checker = new Runnable() {
            public void run() {
                while (myTTS.isSpeaking()) {
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

    public void speak(String text) {
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        notifyWhenReadyWithSpeaking();
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                boolean languageFileOK = myTTS.isLanguageAvailable(LANGUAGE) == TextToSpeech.LANG_AVAILABLE;
                available = languageFileOK;
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                controller.startNewActivityForResult(installIntent, INSTALL_CODE, this);
                available = false;
            }
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void destroy() {
        controller = null;
        if (myTTS != null) {
            myTTS.stop();
            myTTS.shutdown();
            myTTS = null;
        } else {
            Log.w(TAG, "Tried to destroy TextToSpeak, but it is already null");
        }
    }
}
