package edu.radboud.ai.roboud;

import android.util.Log;
import edu.radboud.ai.roboud.behaviour.Behavior;
import edu.radboud.ai.roboud.behaviour.BehaviorFactory;
import edu.radboud.ai.roboud.scenario.Scenario;
import edu.radboud.ai.roboud.scenario.TestScenario;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class RoboudMind implements Observer, Runnable {

    public static final String TAG = "RoboudMind";

    private Thread mindThread;

    private Scenario currentScenario = null;
    private Behavior currentBehavior;

    private RoboudController controller;
    private boolean running;

    private TaskFactory taskFactory;
    private BehaviorFactory behaviorFactory;

    public RoboudMind(RoboudController controller) {
        this.controller = controller;
        currentBehavior = null;
        currentScenario = whatIsCurrentScenario();
        Log.i(TAG, currentScenario.toString());
        behaviorFactory = BehaviorFactory.getInstance(currentScenario, controller);
        running = true;
        mindThread = null;
    }

    private Scenario whatIsCurrentScenario() {
        if (currentScenario == null)
            return new TestScenario();
        else //check whether scenario has changed is not done at the moment
            return currentScenario;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Behavior) {
            currentBehavior = null;
        }
    }

    public Behavior nextBehaviour() {
        // TODO
        return behaviorFactory.getTestBehavior(this);
    }

    public void stopRunning() {
        running = false;
        dispose();
    }

    public void startRunning() {
        running = true;
        if (mindThread == null) {
            mindThread = new Thread(this);
            mindThread.start();
        }
    }

    public void dispose() {
        mindThread.interrupt();
        mindThread = null;
    }

    @Override
    public void run() {

        if (currentBehavior != null)
            currentBehavior.executeBehaviour();

        while (running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentBehavior == null) {
                currentBehavior = nextBehaviour();
                currentBehavior.executeBehaviour();
            }
        }
    }
}
