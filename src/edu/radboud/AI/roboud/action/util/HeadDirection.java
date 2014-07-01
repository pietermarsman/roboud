package edu.radboud.ai.roboud.action.util;

import java.util.Random;

/**
 * Created by Guido on 01-07-14.
 */
public enum HeadDirection {
    UP,
    DOWN;

    public static HeadDirection random() {
        HeadDirection[] val = HeadDirection.values();
        Random r = new Random();
        return val[r.nextInt(val.length)];
    }
}
