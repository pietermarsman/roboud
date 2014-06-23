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
import java.util.Observable;

/**
 * Created by Gebruiker on 22-5-14.
 */
public class AndroidMicrophone extends Observable implements ActivityResultProcessor {

    public static final int REQUEST_CODE = 10;
    private static final String TAG = "AndroidMicrophone";
    private RoboudController controller;
    private boolean available;

    public AndroidMicrophone(RoboudController controller) {
        available = checkIfSpeechRecognitionIsAvailable(controller);
        this.controller = controller;
    }

    public boolean startListening() {
        if (available) {
            controller.stopListeningToRoboMe();
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "AndroidBite Voice Recognition...");
            controller.startNewActivityForResult(intent, REQUEST_CODE, this);
        } else {
            Log.w(TAG, "Microphone is not available");
        }
        return available;
    }

    @Override
    public void processData(int requestCode, int resultCode, Intent data) {
        controller.startListeningToRoboMe();
        Log.i(TAG, "processData. Result code: " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            Log.i(TAG, "processData. Result is OK.");
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            setChanged();
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
