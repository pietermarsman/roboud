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

/**
 * Created by Gebruiker on 22-5-14.
 */
public class AndroidMicrophone extends Observable {

    public static final int REQUEST_CODE = 1234;

    public void startListening(RoboudController activity) {
        activity.stopListeningToRoboMe();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "AndroidBite Voice Recognition...");
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void processData(RoboudController activity, int requestCode, int resultCode, Intent data) {
        activity.startListeningToRoboMe();
        if (resultCode == activity.RESULT_OK) {
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