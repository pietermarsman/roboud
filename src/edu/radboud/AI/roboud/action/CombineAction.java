package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class CombineAction extends AbstractAction implements Observer {

    Action a, b;
    boolean aReady, bReady;

    public CombineAction(RoboudController controller, Action a, Action b) {
        super(controller);
        this.a = a;
        this.b = b;
        aReady = false;
        bReady = false;
    }

    @Override
    public void doActions(Observer abstractBehaviour) {
        // TODO Check if actions can be executed at the same time
        a.doActions(this);
        b.doActions(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable == a)
            aReady = true;
        else if (observable == b)
            bReady = true;
        if (aReady && bReady) {
            setChanged();
            notifyObservers();
        }
    }
}
