package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.action.behaviour.BehaviourBlock;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public interface Task extends BehaviourBlock {
    void isSuitable(Scenario scenario);
}
