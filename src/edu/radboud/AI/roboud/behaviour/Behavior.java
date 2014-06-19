package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.List;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public interface Behavior {

    public List<BehaviorBlock> getBlocks();

    public void executeBehaviour();
}
