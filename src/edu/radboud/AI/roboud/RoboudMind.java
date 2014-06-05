package edu.radboud.ai.roboud;

import edu.radboud.ai.roboud.behaviour.Behaviour;
import edu.radboud.ai.roboud.behaviour.TestBehaviour;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class RoboudMind implements Observer, Runnable {

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
        return new TestBehaviour(controller);
    }

    public void stopRunning() {
        running = false;
        mindThread.interrupt();
    }

    public void startRunning() {
        running = true;
        mindThread = new Thread(this);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.showText("Starting mind");

        // If there was a previous behavior, go on with it
        if (currentBehaviour != null)
            currentBehaviour.executeBehaviour(currentScenario);

        while(running) {
            if (currentBehaviour == null) {
                currentBehaviour = nextBehaviour();
                currentBehaviour.executeBehaviour(currentScenario);
            }
        }
    }
}
