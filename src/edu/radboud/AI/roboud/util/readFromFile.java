package edu.radboud.ai.roboud.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Guido on 25-06-14.
 */
public class ReadFromFile {
    public static final String TAG = "ReadFromFile";
    public ReadFromFile()
    {

    }

    public String readFromFile(String FILENAME) throws IOException {

        FileReader henk;
        try {
            henk = new FileReader(FILENAME);
        } catch (FileNotFoundException e) {
            Log.v(TAG, "file not found");
            return "-1";
        }

        BufferedReader br = new BufferedReader(henk);
        int i;
        String stringFromFile = "";
        do {
            i = br.read();
            if(i != -1)
                stringFromFile += (char) i;
        } while (i != -1);
        Log.v(TAG,"Just read this: " + stringFromFile);
        br.close();
        henk.close();
        return stringFromFile;
    }
}
