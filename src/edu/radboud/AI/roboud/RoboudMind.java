package edu.radboud.ai.roboud;

import edu.radboud.ai.roboud.behaviour.Behaviour;
import edu.radboud.ai.roboud.behaviour.TestBehavior;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class RoboudMind implements Observer, Runnable {

    public static final String TAG = "RoboudMind";

    private Thread mindThread;

    private Scenario currentScenario;
    private Behaviour currentBehaviour;

    private RoboudController controller;
    private boolean running;

    public RoboudMind(RoboudController controller) {
        this.controller = controller;
        this.currentBehaviour = null;
        this.currentScenario = whatIsCurrentScenario();
        running = true;
        mindThread = null;
    }

    private Scenario whatIsCurrentScenario() {
        // TODO
        return null;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Behaviour) {
            currentBehaviour = null;
        }
    }

    public Behaviour nextBehaviour() {
        // TODO
        return new TestBehavior(controller, this);
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (currentBehaviour != null)
            currentBehaviour.executeBehaviour(whatIsCurrentScenario());

        while (running) {
            if (currentBehaviour == null) {
                currentBehaviour = nextBehaviour();
                currentBehaviour.executeBehaviour(whatIsCurrentScenario());
            }
        }
    }
}
