package edu.radboud.ai.roboud;

import edu.radboud.ai.roboud.behaviour.Behaviour;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class RoboudMind implements Observer, Runnable {

    private Scenario currentScenario;
    private Behaviour currentBehaviour;

    private RoboudController controller;
    private boolean running;

    public RoboudMind(RoboudController controller) {
        this.controller = controller;
        this.currentBehaviour = null;
        this.currentScenario = whatIsCurrentScenario();
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
        return null;
    }

    public void stopRunning() {
        running = false;
    }

    @Override
    public void run() {
        while(running) {
            if (currentBehaviour == null) {
                currentBehaviour = nextBehaviour();
                currentBehaviour.executeBehaviour(currentScenario);
            }
        }
    }
}
