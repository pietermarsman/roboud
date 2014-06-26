package edu.radboud.ai.roboud.senses;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.util.ActivityResultProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

/**
 * Created by Gebruiker on 22-5-14.
 */
public class AndroidMicrophone extends Observable implements ActivityResultProcessor {

    public static final int REQUEST_CODE = 10;
    private static final String TAG = "AndroidMicrophone";
    private static AndroidMicrophone instance;
    private RoboudController controller;
    private boolean available;

    private AndroidMicrophone(RoboudController controller) {
        available = checkIfSpeechRecognitionIsAvailable(controller);
        this.controller = controller;
    }

    public static synchronized AndroidMicrophone getInstance(RoboudController controller) {
        if (instance == null)
            instance = new AndroidMicrophone(controller);
        return instance;
    }

    public boolean startListening() {
        if (available) {
            controller.stopListeningToRoboMe();
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.US.toString());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "I am listening");
            controller.startNewActivityForResult(intent, REQUEST_CODE, this);
        } else {
            Log.w(TAG, "Microphone is not available");
        }
        return available;
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        controller.startListeningToRoboMe();
        Log.i(TAG, "processData. Request code: " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            Log.i(TAG, "processData. Result is OK.");
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            setChanged();
            // Give the data directly to the observer
            notifyObservers(results);
        }
    }

    /**
     * Disable button if no recognition service is present
     */
    private boolean checkIfSpeechRecognitionIsAvailable(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        boolean hasMicrophone = pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);

        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        boolean hasSpeechRecognition = activities.size() > 0;
        return hasMicrophone && hasSpeechRecognition;
    }

    public boolean isAvailable() {
        return available;
    }
}
