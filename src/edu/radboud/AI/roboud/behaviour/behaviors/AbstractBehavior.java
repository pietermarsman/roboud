package edu.radboud.ai.roboud.behaviour.behaviors;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.action.actions.AbstractAction;
import edu.radboud.ai.roboud.action.pools.ActionPool;
import edu.radboud.ai.roboud.behaviour.util.BehaviorBlock;
import edu.radboud.ai.roboud.task.TaskFactory;
import edu.radboud.ai.roboud.task.tasks.AbstractTask;

import java.util.*;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractBehavior extends Observable implements Observer {

    public static final String TAG = "AbstractBehavior";
    protected List<BehaviorBlock> blocks;
    protected TaskFactory taskFactory;
    protected ActionFactory actionFactory;
    protected LinkedHashMap<BehaviorBlock, Object> results;
    private int executionIndex;

    public AbstractBehavior(ActionFactory actionFactory, TaskFactory taskFactory) {
        this.actionFactory = actionFactory;
        this.taskFactory = taskFactory;
        blocks = new LinkedList<BehaviorBlock>();
        results = new LinkedHashMap<BehaviorBlock, Object>();
        executionIndex = -1;
    }

    public List<BehaviorBlock> getBlocks() {
        return blocks;
    }

    /**
     * Execute the BehaviorBlock one by one, starting a new block if the previous block has ended
     */
    public synchronized void executeBehaviour() {
        executeStep(null);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof BehaviorBlock) {
            BehaviorBlock currentBlock = (BehaviorBlock) observable;
            results.put(currentBlock, currentBlock.getInformation());
            currentBlock.deleteObserver(this);
            executeStep(processInformation(currentBlock));
        }
    }

    private synchronized void executeStep(Object information) {
        if (executionIndex + 1 < blocks.size()) {
            executionIndex++;
            Log.i(TAG, "Execute step " + executionIndex);
            BehaviorBlock currentBlock = blocks.get(executionIndex);
            currentBlock.addObserver(this);
            currentBlock.doActions(information);

        } else {
            Log.i(TAG, "No further steps to execute");
            releaseActions();
            setChanged();
            notifyObservers();
        }
    }

    protected abstract Object processInformation(BehaviorBlock currentBlock);

    public abstract Object getInformation();

    private void releaseActions(){
        for (Iterator<BehaviorBlock> it = blocks.iterator(); it.hasNext();){
            BehaviorBlock block = it.next();
            if (block instanceof AbstractTask){
                AbstractTask task = (AbstractTask) block;
                task.releaseActions();
            }
            else if (block instanceof AbstractAction){
                AbstractAction action = (AbstractAction) block;
                actionFactory.releaseAction(action);
            }
        }
    }
}