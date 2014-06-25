package edu.radboud.ai.roboud.task;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.behaviour.BehaviorBlock;

import java.util.*;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractTask extends BehaviorBlock implements Observer {

    public static final String TAG = "AbstractTask";
    protected List<AbstractAction> actions;
    protected RoboudController controller;
    protected LinkedHashMap<BehaviorBlock, Object> results;
    private int executionIndex;

    public AbstractTask(RoboudController controller) {
        this.controller = controller;
        actions = new LinkedList<AbstractAction>();
        results = new LinkedHashMap<BehaviorBlock, Object>();
    }

    /**
     * Execute the Actions that make this task. Start one action once the previous has ended
     *
     * @param information
     */
    @Override
    public void doActions(Object information) {
        executionIndex = -1;
        processTaskInformation(information);
        executeStep(null);
    }

    private void executeStep(Object information) {
        if (executionIndex + 1 < actions.size()) {
            executionIndex++;
            BehaviorBlock selectedAction = actions.get(executionIndex);
            selectedAction.addObserver(this);
            Log.i(TAG, "Execute action #" + executionIndex + " " + selectedAction.getClass().getSimpleName());
            selectedAction.doActions(information);
        } else {
            Log.i(TAG, "No further actions to execute");
            releaseActions();
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof BehaviorBlock) {
            BehaviorBlock currentBlock = (BehaviorBlock) observable;
            results.put(currentBlock, currentBlock.getInformation());
            executeStep(processActionInformation());
        }
    }

    public abstract void releaseActions();

    //This might need to be transferred to specific actions
    @Override
    public Object getInformation() {
        return null;
    }

    protected abstract Object processActionInformation();

    protected abstract void processTaskInformation(Object information);
}
