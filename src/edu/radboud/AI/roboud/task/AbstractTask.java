package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.action.Action;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractTask extends Observable implements Task, Observer {

    protected List<Action> actions;
    private int executionIndex;


    public AbstractTask(){
        actions = new LinkedList<Action>();
    }
    /**
     * Execute the Actions that make this task. Start one action once the previous has ended
     *
     * @param abstractBehaviour
     */
    @Override
    public void doActions(Observer abstractBehaviour) {
        executionIndex = 0;
        executeStep();
    }

    protected void executeStep() {
        actions.get(executionIndex).doActions(this);
        executionIndex++;
    }
}
