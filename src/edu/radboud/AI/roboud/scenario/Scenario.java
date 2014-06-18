package edu.radboud.ai.roboud.scenario;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public interface Scenario {

    public boolean isCanTalk();
    public boolean isCanListen();
    public boolean isCanDrive();
    public boolean isInteractingWithIndividual();
}
