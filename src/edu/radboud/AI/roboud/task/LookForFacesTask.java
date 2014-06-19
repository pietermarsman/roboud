package edu.radboud.ai.roboud.task;

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
}
