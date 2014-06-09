package edu.radboud.ai.roboud.action;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.radboud.ai.roboud.R;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class ChoiceActionActivity extends ListActivity implements AdapterView.OnItemClickListener {

    public static final String RETURN_NAME = "Selected Item";

    private ListView list;
    private String[] options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.choice);

        list = getListView();

        Intent sender = getIntent();
        options = sender.getExtras().getStringArray(ChoiceAction.DATA_NAME);

        list.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, options));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = options[position];
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RETURN_NAME, selectedItem);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}