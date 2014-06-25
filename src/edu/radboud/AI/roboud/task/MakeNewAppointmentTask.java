package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.AbstractAction;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class MakeNewAppointmentTask extends AbstractTask {

    public MakeNewAppointmentTask(RoboudController controller, AbstractAction output, AbstractAction input) {
        super(controller);
        // TODO
    }

    @Override
    public void releaseActions() {
        //TODO
    }

    @Override
    protected Object processActionInformation() {
        return null;
    }

    @Override
    protected void processTaskInformation(Object information) {

    }
}
