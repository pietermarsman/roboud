package edu.radboud.ai.roboud.action.util;

import java.util.Random;

/**
 * Created by Guido on 01-07-14.
 */
public enum HeadDirection {
    CENTER, // center the head, also referred to as reset.
    UP200, // 200 milliseconds, etc.
    DOWN200,
    UP500,
    DOWN500,
    ALLUP,
    ALLDOWN;

    public static HeadDirection random() {
        HeadDirection[] val = HeadDirection.values();
        Random r = new Random();
        return val[r.nextInt(val.length)];
    }
}
