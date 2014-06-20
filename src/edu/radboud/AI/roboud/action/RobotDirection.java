package edu.radboud.ai.roboud.action;

import java.util.Random;

/**
 * @author Mike Ligthart
 */
public enum RobotDirection {
    FORWARD,
    RIGHT,
    BACKWARD,
    LEFT;

    public static RobotDirection random() {
        RobotDirection[] val = RobotDirection.values();
        Random r = new Random();
        return val[r.nextInt(val.length)];
    }
}
