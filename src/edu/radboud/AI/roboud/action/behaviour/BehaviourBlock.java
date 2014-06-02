package edu.radboud.ai.roboud.action.behaviour;

import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public interface BehaviourBlock {

    public void doActions(Scenario scenario);
    public boolean isFinished();
}
