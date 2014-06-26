package edu.radboud.ai.roboud.util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Guido on 25-06-14.
 */
public class WriteToFile {
    public static final String TAG = "WriteToFile";

    public WriteToFile() {
    }

    public void writeToFile(String toWrite, String FILENAME) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME));
        Random r = new Random();
        int intToAdd = r.nextInt();
        Log.v(TAG, "Next random int to store in memory: " + intToAdd);
        bw.write(toWrite + " random int: " + intToAdd);
        bw.close();
    }
}
