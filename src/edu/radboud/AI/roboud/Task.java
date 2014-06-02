package edu.radboud.ai.roboud;

import edu.radboud.ai.roboud.behaviour.BehaviourBlock;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public interface Task extends BehaviourBlock {
    void isSuitable(Scenario scenario);
}
