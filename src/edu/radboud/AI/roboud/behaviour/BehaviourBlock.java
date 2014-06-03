package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observer;
import edu.radboud.AI.roboud;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public interface BehaviourBlock {

    public void doActions(Scenario scenario, Observer abstractBehaviour, RoboudController roboudController);

}
