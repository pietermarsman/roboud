package edu.radboud.AI.roboud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 20-5-14.
 * Based on: http://stackoverflow.com/questions/8499042/android-audiorecord-example
 */
public class AndroidMicrophone extends Activity implements View.OnClickListener {

    String TAG = "AndroidMicrophone";

    private TextView logView;

//    private Activity context;
//    private RoboudController controller;

    public void onCreate(Bundle savedInstanceState) {
//        this.context = _context;
//        this.controller = _controller;


        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        logView = (TextView) findViewById(R.id.output);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
    }

    private static final int REQUEST_CODE = 1234;

    public void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "AndroidBite Voice Recognition...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void stopListening() {

    }

    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            logView.setText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        startListening();
    }
}
