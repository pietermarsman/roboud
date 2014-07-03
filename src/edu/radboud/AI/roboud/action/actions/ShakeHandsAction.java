package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.action.util.RobotDirection;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mikel_000 on 3-7-2014.
 */
public class ShakeHandsAction extends AbstractAction implements Observer {


    private final static String TAG = "ShakeHandsAction";
    private RoboudModel model;
    private boolean succes;

    public ShakeHandsAction(RoboudController controller) {
        super(controller);
        model = controller.getModel();
        succes = false;
    }

    @Override
    public void doActions(Object information) {
        model.addObserver(this);
    }

    @Override
    public Object getInformation() {
        return succes;
    }

    public boolean getSucces(){
        return succes;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof RoboudModel){
            if (model.getRobomeHandshakeStatus() >= 0){
                succes = true;
                setChanged();
                notifyObservers();
            }
        }
    }
}
