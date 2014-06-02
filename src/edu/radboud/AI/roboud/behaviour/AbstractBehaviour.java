package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractBehaviour extends Observable implements Behaviour, Observer {

    protected List<BehaviourBlock> blocks;
    private int executionIndex = 0;
    private Scenario scenario;

    public List<BehaviourBlock> getBlocks() {
        return blocks;
    }

    /**
     * Execute the BehaviourBlock one by one, starting a new block if the previous block has ended
     * @param scenario
     */
    public void executeBehaviour(Scenario scenario) {
        this.scenario = scenario;
        executeStep();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof BehaviourBlock)
            executeStep();
    }

    private void executeStep() {
        if (executionIndex < blocks.size()) {
            blocks.get(executionIndex).doActions(scenario, this);
            executionIndex++;
        } else {
            setChanged();
            notifyObservers();
        }
    }
}