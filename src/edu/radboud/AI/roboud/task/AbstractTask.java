package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.action.Action;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public abstract class AbstractTask extends Observable implements Task, Observer {

    protected List<Action> actions;
    private int executionIndex;
    private Scenario scenario;

    /**
     * Execute the Actions that make this task. Start one action once the previous has ended
     *
     * @param scenario
     * @param abstractBehaviour
     */
    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        this.scenario = scenario;
        executionIndex = 0;
        executeStep();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Action)
            executeStep();
    }

    private void executeStep() {
        actions.get(executionIndex).doActions(scenario, this);
        executionIndex++;
    }
}
