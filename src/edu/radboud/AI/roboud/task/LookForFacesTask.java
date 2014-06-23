package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.RoboudController;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class LookForFacesTask extends AbstractTask {

    public LookForFacesTask(RoboudController controller, boolean enabled) {
        super(controller);

        if (enabled) {
            // TODO
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void releaseActions() {

    }

    @Override
    protected Object processInformation() {
        return null;
    }
}
