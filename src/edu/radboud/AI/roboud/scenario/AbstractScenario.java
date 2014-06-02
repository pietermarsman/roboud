package edu.radboud.ai.roboud.scenario;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public abstract class AbstractScenario implements Scenario {

    public boolean canTalk, canDrive, interactingWithIndividual;

    public AbstractScenario() {
        canTalk = true;
        canDrive = true;
        interactingWithIndividual = true;
    }
}
