package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;

/**
 * Created by Guido on 01-07-14.
 */
public class HeadAction extends AbstractAction{
    private final static String TAG = "HeadAction";

    public HeadAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
//        if (information != null && information instanceof LedColor) {
//            color = (LedColor) information;
//        }

        setChanged();
        notifyObservers();
    }

    @Override
    public Object getInformation() {
        return null;
    }
}
