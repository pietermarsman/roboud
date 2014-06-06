package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;

import java.util.List;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 27-5-2014.
 */
public class MakeNewApointmentBehavior extends AbstractBehavior {

    public MakeNewApointmentBehavior(RoboudController controller, Observer observer) {
        super(controller, observer);
    }

    @Override
    public List<BehaviourBlock> getBlocks() {
        return null;
    }
}
