package edu.radboud.ai.roboud.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Guido on 20-06-14.
 */
public class StoreInformation extends Activity {
    public final static String TAG = "StoreInformation";
    public static final String PREFS_NAME = "saveFileName";
    boolean boolean1;

    public StoreInformation(Bundle state) {
        Log.v(TAG, "In constructor");
    }

    @Override
    protected void onCreate(Bundle state) {
        Log.v(TAG, "Oncreate now");
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // (0 = MODE_PRIVATE)
        boolean boolean1 = settings.getBoolean("boolean1", false);
        String StringName1 = settings.getString("StringName1", "defaultValue");
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("boolean1", true);
        editor.putString("StringName1", "InhoudString1");
        editor.commit();
    }


}
