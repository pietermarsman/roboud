package edu.radboud.ai.roboud.action;

import java.util.Random;

/**
 * @author Mike Ligthart
 */
public enum RobotSpeed {
    FASTEST,
    FAST,
    NORMAL,
    SLOW,
    SLOWEST;

    public static RobotSpeed random() {
        RobotSpeed[] val = RobotSpeed.values();
        Random r = new Random();
        return val[r.nextInt(val.length)];
    }
}
