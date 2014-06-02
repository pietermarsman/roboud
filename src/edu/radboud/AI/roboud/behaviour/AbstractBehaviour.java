package edu.radboud.ai.roboud.behaviour;

import java.util.List;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractBehaviour implements Behaviour {

    private List<BehaviourBlock> blocks;

    public void setBlocks(List<BehaviourBlock> blocks) {
        this.blocks = blocks;
    }
}