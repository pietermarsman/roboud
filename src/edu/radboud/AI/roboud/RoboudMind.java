package edu.radboud.ai.roboud;

import edu.radboud.ai.roboud.behaviour.Behaviour;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class RoboudMind {

    private Scenario currentScenario;
    private Behaviour currentBehaviour;

    private RoboudController controller;

    public RoboudMind(RoboudController controller, Scenario currentScenario, Behaviour currentBehaviour) {
        this.currentBehaviour = currentBehaviour;
        this.currentScenario = currentScenario;
        this.controller = controller;
    }
}
