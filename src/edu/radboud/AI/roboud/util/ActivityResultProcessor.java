package edu.radboud.ai.roboud.util;

import android.content.Intent;

/**
 * Created by Pieter Marsman on 5-6-2014.
 */
public interface ActivityResultProcessor {

    public void processData(int requestCode, int resultCode, Intent data);
}
