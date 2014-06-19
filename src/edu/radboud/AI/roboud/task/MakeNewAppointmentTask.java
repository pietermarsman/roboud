package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.action.AbstractAction;
import edu.radboud.ai.roboud.action.Action;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class MakeNewAppointmentTask extends AbstractTask {

    public MakeNewAppointmentTask(AbstractAction output, AbstractAction input) {
        super();
        // TODO
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Action) {
            executeStep();
        }
    }

}
