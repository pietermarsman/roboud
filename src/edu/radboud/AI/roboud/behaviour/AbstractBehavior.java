package edu.radboud.ai.roboud.behaviour;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.task.TaskFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractBehavior extends Observable implements Behavior, Observer {

    public static final String TAG = "AbstractBehavior";
    protected List<BehaviorBlock> blocks;
    protected RoboudController controller;
    protected TaskFactory taskFactory;
    private int executionIndex;

    public AbstractBehavior(RoboudController controller, TaskFactory taskFactory, Observer observer) {
        this.addObserver(observer);
        this.controller = controller;
        this.taskFactory = taskFactory;
        blocks = new LinkedList<BehaviorBlock>();
        executionIndex = -1;
    }

    public List<BehaviorBlock> getBlocks() {
        return blocks;
    }

    /**
     * Execute the BehaviorBlock one by one, starting a new block if the previous block has ended
     */
    public void executeBehaviour() {
        executeStep();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof BehaviorBlock)
            executeStep();
    }

    private void executeStep() {
        if (executionIndex + 1 < blocks.size()) {
            executionIndex++;
            Log.i(TAG, "Execute step " + executionIndex);
            BehaviorBlock currentBlock = blocks.get(executionIndex);
            currentBlock.addObserver(this);
            currentBlock.doActions();

        } else {
            Log.i(TAG, "No further steps to execute");
            releaseActions();
            setChanged();
            notifyObservers();
        }
    }

    public abstract void releaseActions();
}