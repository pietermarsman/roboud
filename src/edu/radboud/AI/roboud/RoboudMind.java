package edu.radboud.ai.roboud;

import android.util.Log;
import edu.radboud.ai.roboud.behaviour.AbstractBehavior;
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

    private static RoboudMind instance = null;
    private Thread mindThread;

    private Scenario currentScenario = null;
    private AbstractBehavior currentBehavior;

    private RoboudController controller;
    private boolean running;

    private TaskFactory taskFactory;
    private BehaviorFactory behaviorFactory;
    private boolean runningForTheFirstTime;

    private RoboudMind(RoboudController controller) {
        this.controller = controller;
        currentBehavior = null;
        currentScenario = whatIsCurrentScenario();
        behaviorFactory = BehaviorFactory.getInstance(currentScenario, controller);
        running = false;
        mindThread = null;
        runningForTheFirstTime = true;
    }

    public static synchronized RoboudMind getInstance(RoboudController controller) {
        if (instance == null)
            instance = new RoboudMind(controller);
        return instance;
    }

    private Scenario whatIsCurrentScenario() {
        if (currentScenario == null)
            return controller.getModel().getScenario();
        else //check whether scenario has changed is not done at the moment
            return currentScenario;
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i(TAG, "==Mind is updated==");
        if (observable instanceof AbstractBehavior) {
            currentBehavior.deleteObservers();
            currentBehavior = null;
            Log.i(TAG, "currentBehavior is reset");
        }
    }

    public synchronized AbstractBehavior nextBehaviour() {
        // TODO
        AbstractBehavior behavior = behaviorFactory.getTestBehavior();
        //AbstractBehavior behavior = behaviorFactory.getDutchGoalBehavior();
        behavior.addObserver(this);
        return behavior;
    }

    public void stopRunning() {
        running = false;
        dispose();
    }

    public void startRunning() {
        Log.d(TAG, "Start running roboud mind");
        if (mindThread == null) {
            mindThread = new Thread(this);
            running = true;
            mindThread.start();
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
        if (currentBehavior != null && runningForTheFirstTime) {
            currentBehavior.executeBehaviour();
            runningForTheFirstTime = false;
        }

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
