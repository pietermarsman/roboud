package edu.radboud.ai.roboud.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Guido on 20-06-14.
 */
public class StoreInformation extends Activity {
    public final static String TAG = "StoreInformation";
    public static final String PREFS_NAME = "saveFileName";
    private boolean boolean1;
    private String StringName1;
    private int int1;

    public StoreInformation(Bundle state) {
        Log.v(TAG, "In constructor");
    }

    @Override
    protected void onCreate(Bundle state)
    {
        Log.v(TAG, "Oncreate storeinformation");
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // (0 = MODE_PRIVATE)
        for(int i = 0; i < state.size(); i++)
        {
//            boolean1 = state.getBoolean("boolean1");
//            StringName1 = state.getString("StringName1");
//            int1 = state.getInt("int1");
        }
    }

    @Override
    protected void onStop() {
        Log.v(TAG,"onStop StoreInformation");



        super.onStop();
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putBoolean("boolean1", boolean1);
//        editor.putString("StringName1", StringName1);
//        editor.commit();
    }
}
