package edu.radboud.ai.roboud.behaviour;

import android.os.Bundle;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.StoreInformation;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observer;

/**
 * Created by Pieter Marsman on 4-6-2014.
 */
public class TestBehavior extends AbstractBehavior {

    public final static String TAG = "TestBehavior";

    public TestBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        super(controller, taskFactory, observer);


    }
}
// Tested and working:
// LEDAction
// RandomWander
// ShowTextAction