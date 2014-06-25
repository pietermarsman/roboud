package edu.radboud.ai.roboud;

import android.util.Log;
import edu.radboud.ai.roboud.behaviour.Behavior;
import edu.radboud.ai.roboud.behaviour.BehaviorFactory;
import edu.radboud.ai.roboud.scenario.Scenario;
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
        behaviorFactory = BehaviorFactory.getInstance(currentScenario, controller);
        running = false;
        mindThread = null;
    }

    private Scenario whatIsCurrentScenario() {
        if (currentScenario == null)
            return controller.getModel().getScenario();
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
        //return behaviorFactory.getDutchGoalBehavior(this);
    }

    public void stopRunning() {
        dispose();
        running = false;
    }

    public void startRunning() {
        Log.d(TAG, "Start running roboud mind");
        if (mindThread == null) {
            mindThread = new Thread(this);
            mindThread.start();
            running = true;
        } else {
            Log.w(TAG, "Already running, no need to start it again");
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void dispose() {
        if (mindThread != null) {
            mindThread.interrupt();
            mindThread = null;
        }
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
