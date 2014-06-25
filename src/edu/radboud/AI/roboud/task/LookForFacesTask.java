package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class LookForFacesTask extends AbstractTask {

    public LookForFacesTask(RoboudController controller, boolean canDrive) {
        super(controller);

        controller.getModel().getFaces();
    }

    @Override
    public void releaseActions() {

    }

    @Override
    protected Object processActionInformation() {
        return null;
    }

    @Override
    protected void processTaskInformation(Object information) {

    }
}
