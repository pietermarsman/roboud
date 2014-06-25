package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.MotorAction;
import edu.radboud.ai.roboud.action.util.RobotDirection;
import edu.radboud.ai.roboud.action.util.RobotSpeed;


/**
 * Created by Mike Ligthart on 22-6-2014.
 */
public class MotorActionPool extends ActionPool<MotorAction> {

    private static MotorActionPool instance = null;

    private MotorActionPool(RoboudController controller) {
        super(controller);
    }

    public static synchronized MotorActionPool getInstance(RoboudController controller) {
        if (instance == null)
            instance = new MotorActionPool(controller);
        return instance;
    }

    public MotorAction acquire(RobotDirection dir, RobotSpeed speed) {
        MotorAction motorAction = acquire();
        motorAction.setDirAndSpeed(dir, speed);
        return motorAction;
    }

    @Override
    protected MotorAction create() {
        return new MotorAction(controller);
    }
}
