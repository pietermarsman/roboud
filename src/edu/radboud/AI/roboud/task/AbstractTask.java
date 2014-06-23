package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.behaviour.BehaviorBlock;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractTask extends BehaviorBlock implements Observer {

    public static final String TAG = "AbstractTask";
    protected List<AbstractAction> actions;
    private int executionIndex;
    protected RoboudController controller;

    public AbstractTask(RoboudController controller) {
        this.controller = controller;
        actions = new LinkedList<AbstractAction>();

    }

    /**
     * Execute the Actions that make this task. Start one action once the previous has ended
     */
    @Override
    public void doActions() {
        executionIndex = -1;
        executeStep();
    }

    private void executeStep() {
        if (executionIndex + 1 < actions.size()) {
            executionIndex++;
            BehaviorBlock selectedAction = actions.get(executionIndex);
            selectedAction.addObserver(this);
            Log.i(TAG, "Execute action #" + executionIndex + " " + selectedAction.getClass().getSimpleName());
            selectedAction.doActions();
        } else {
            Log.i(TAG, "No further actions to execute");
            releaseActions();
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AbstractAction) {
            executeStep();
        }
    }

    public abstract void releaseActions();
}
