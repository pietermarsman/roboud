package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Pieter Marsman on 19-6-2014.
 */
public class BehaviorFactory {


    private final static String TAG = "BehaviorFactory";
    private static BehaviorFactory instance = null;
    private Scenario scenario;
    private RoboudController controller;
    private TaskFactory taskFactory;

    private BehaviorFactory(Scenario scenario, RoboudController controller) {
        this.scenario = scenario;
        this.controller = controller;
        taskFactory = TaskFactory.getInstance(scenario, controller);
    }

    public synchronized static BehaviorFactory getInstance(Scenario scenario, RoboudController controller) {
        if (instance == null || !instance.equals(scenario))
            instance = new BehaviorFactory(scenario, controller);
        return instance;
    }

    public TestBehavior getTestBehavior() {
        return new TestBehavior(controller, taskFactory);
    }

    public DutchGoalBehavior getDutchGoalBehavior() {
        return new DutchGoalBehavior(controller, taskFactory);
    }
}
