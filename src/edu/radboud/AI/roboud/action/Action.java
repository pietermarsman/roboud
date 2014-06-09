package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.behaviour.BehaviourBlock;
import edu.radboud.ai.roboud.scenario.Scenario;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public interface Action extends BehaviourBlock {

    /**
     * Code that is actually executing the action.
     * Abstract action provides a layer on top of this method that makes sure that is implementing a BehaviorBlock.
     * @param scenario
     */
    public void executeAction(Scenario scenario);
}
