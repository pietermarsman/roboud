package edu.radboud.ai.roboud.action.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import edu.radboud.ai.roboud.R;

/**
 * Created by Pieter Marsman on 26-6-2014.
 */
public class ReadTextActionActivity extends Activity implements View.OnClickListener {

    public static final String RETURN_NAME = "ReadText";
    private static final String TAG = "ReadTextActionActivity";

    private EditText editText;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.face_read_text);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String text = editText.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RETURN_NAME, text);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}