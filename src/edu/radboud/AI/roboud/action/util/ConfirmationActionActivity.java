package edu.radboud.ai.roboud.action.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.radboud.ai.roboud.R;
import edu.radboud.ai.roboud.action.actions.ConfirmationAction;

/**
 * Created by Pieter Marsman on 9-6-2014.
 */
public class ConfirmationActionActivity extends Activity implements View.OnClickListener {

    public static final String RETURN_NAME = "result";
    private TextView textView;
    private Button yesButton, noButton;

    private String question;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_confirmation);

        textView = (TextView) findViewById(R.id.textView);
        yesButton = (Button) findViewById(R.id.confirmation_button_yes);
        noButton = (Button) findViewById(R.id.confirmation_button_no);

        Intent sender = getIntent();
        question = sender.getExtras().getString(ConfirmationAction.DATA_NAME);

        textView.setText(question);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean result = false;
        if (v == yesButton)
            result = true;
        else if (v == noButton)
            result = false;

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RETURN_NAME, result);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
