package edu.radboud.ai.roboud.action.behaviour;

import java.util.List;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractBehaviour implements Behaviour {

    protected List<BehaviourBlock> blocks;

    public List<BehaviourBlock> getBlocks() {
        return blocks;
    }
}