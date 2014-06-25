package edu.radboud.ai.roboud.action.actions;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.util.FaceExpression;

/**
 * Created by Pieter Marsman on 24-6-2014.
 */
public class ExpressEmotionAction extends AbstractAction {

    private static final String TAG = "ExpressEmotionAction";
    FaceExpression expression;

    public ExpressEmotionAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Object information) {
        controller.setFaceExpression(expression);
        setChanged();
        notifyObservers();
    }

    @Override
    public Object getInformation() {
        return null;
    }

    public void setExpression(FaceExpression expression) {
        this.expression = expression;
    }
}
