package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class CombineAction extends AbstractAction implements Observer {

    AbstractAction a, b;
    boolean aReady, bReady;

    @Deprecated
    public CombineAction(RoboudController controller, AbstractAction a, AbstractAction b) {
        super(controller);
        this.a = a;
        this.b = b;
        aReady = false;
        bReady = false;
    }

    public CombineAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        if (a == null && b == null){
            throw new NullPointerException("actions cannot be null");
        }
        // TODO Check if actions can be executed at the same time
        a.doActions(information);
        b.doActions(information);
    }

    @Override
    public Object getInformation() {
        return null;
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

    public void setActions(AbstractAction a, AbstractAction b) {
        this.a = a;
        this.b = b;
    }
}
