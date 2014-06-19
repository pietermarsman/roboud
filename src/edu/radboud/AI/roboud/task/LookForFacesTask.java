package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.action.Action;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class LookForFacesTask extends AbstractTask {


    public LookForFacesTask(boolean enabled) {
        super();
        if(enabled){
            // TODO
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Action) {
            executeStep();
        }
    }
}
