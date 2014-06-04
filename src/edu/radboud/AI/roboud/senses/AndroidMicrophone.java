package edu.radboud.ai.roboud.senses;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import edu.radboud.ai.roboud.RoboudController;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Gebruiker on 22-5-14.
 */
public class AndroidMicrophone extends Observable {

    public static final int REQUEST_CODE = 1234;
    private RoboudController controller;

    public AndroidMicrophone(RoboudController controller) {
        this.controller = controller;
    }

    public void startListening(Observer observer) {
        this.addObserver(observer);
        controller.stopListeningToRoboMe();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "AndroidBite Voice Recognition...");
        controller.startActivityForResult(intent, REQUEST_CODE);
    }

    public void processData(int requestCode, int resultCode, Intent data) {
        controller.startListeningToRoboMe();
        if (resultCode == Activity.RESULT_OK) {
             ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            setChanged();
            notifyObservers(results);
        }
    }

    /**
     * Disable button if no recognition service is present
      */
    public boolean checkIfSpeechRecognitionIsAvailable(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return activities.size() == 0;
    }
}
