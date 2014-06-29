package edu.radboud.ai.roboud.behaviour.util;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class BehaviorBlock extends Observable {

    public abstract void doActions(Object information);

    public abstract Object getInformation();

}
