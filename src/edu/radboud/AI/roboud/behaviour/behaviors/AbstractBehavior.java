package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.*;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractBehavior extends Observable implements Observer {

    public static final String TAG = "AbstractBehavior";

    protected List<AbstractAction> actions;
    protected ActionFactory actionFactory;
    protected LinkedHashMap<AbstractAction, Object> results;
    protected Scenario scenario;

    private int executionIndex;

    public AbstractBehavior(ActionFactory actionFactory, Scenario scenario) {
        this.actionFactory = actionFactory;
        this.scenario = scenario;
        actions = new LinkedList<AbstractAction>();
        results = new LinkedHashMap<AbstractAction, Object>();
        executionIndex = -1;
    }

    public List<AbstractAction> getActions() {
        return actions;
    }

    /**
     * Execute the AbstractAction one by one, starting a new block if the previous block has ended
     */
    public synchronized void executeBehaviour() {
        executeStep(null);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof AbstractAction) {
            AbstractAction currentAction = (AbstractAction) observable;
            results.put(currentAction, currentAction.getInformation());
            currentAction.deleteObserver(this);
            executeStep(processInformation(currentAction));
        }
    }

    private synchronized void executeStep(Object information) {
        if (executionIndex + 1 < actions.size()) {
            executionIndex++;
            Log.i(TAG, "Execute step " + executionIndex);
            AbstractAction currentAction = actions.get(executionIndex);
            currentAction.addObserver(this);
            currentAction.doActions(information);

        } else {
            Log.i(TAG, "No further steps to execute");
            releaseActions();
            setChanged();
            notifyObservers();
        }
    }

    protected abstract Object processInformation(AbstractAction currentAction);

    public abstract Object getInformation();

    private void releaseActions() {
        for (Iterator<AbstractAction> it = actions.iterator(); it.hasNext(); ) {
            actionFactory.releaseAction(it.next());
        }
    }
}