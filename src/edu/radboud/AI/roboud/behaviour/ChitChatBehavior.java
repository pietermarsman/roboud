package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;

import java.util.List;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class ChitChatBehavior extends AbstractBehavior {

    public ChitChatBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
    }

    @Override
    public List<BehaviourBlock> getBlocks() {
        return null;
    }
}
