package edu.radboud.ai.roboud.task;

import edu.radboud.ai.roboud.action.Action;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.List;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class AbstractTask implements Task {
    private List<Action> actions;
    @Override
    public void isSuitable(Scenario scenario) {

    }

    @Override
    public void doActions(Scenario scenario) {

    }

    @Override
    public boolean isFinished() {

    }
}
