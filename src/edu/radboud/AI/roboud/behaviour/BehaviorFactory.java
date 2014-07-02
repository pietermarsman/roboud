package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.behaviour.behaviors.*;
import edu.radboud.ai.roboud.util.Scenario;

/**
 * Created by Pieter Marsman on 19-6-2014.
 */
public class BehaviorFactory {


    private final static String TAG = "BehaviorFactory";
    private static BehaviorFactory instance = null;
    private ActionFactory actionFactory;
    private Scenario scenario;

    private BehaviorFactory(Scenario scenario, RoboudController controller) {
        this.scenario = scenario;
        actionFactory = ActionFactory.getInstance(controller);
    }

    public synchronized static BehaviorFactory getInstance(Scenario scenario, RoboudController controller) {
        if (instance == null || !instance.equals(scenario))
            instance = new BehaviorFactory(scenario, controller);
        return instance;
    }

    public TestBehavior getTestBehavior() {
        return new TestBehavior(actionFactory, scenario);
    }

    public IntroduceBehavior getIntroduceBehavior() {
        return new IntroduceBehavior(actionFactory, scenario);
    }

    public AreWeFamiliarBehavior getAreWeFamiliarBehavior() {
        return new AreWeFamiliarBehavior(actionFactory, scenario);
    }

    public CountNrPeopleBehavior getCountNrPeopleBehavior(){
        return new CountNrPeopleBehavior(actionFactory, scenario);
    }

    public SelectExistingUserBehavior getExistingUserBehavior() {
        return new SelectExistingUserBehavior(actionFactory, scenario);
    }

    public SettingsBehavior getSettingsBehavior() {
        return new SettingsBehavior(actionFactory, scenario);
    }

    public TurnMeOffBehavior getTurnMeOffBehavior() {
        return new TurnMeOffBehavior(actionFactory, scenario);
    }

    public RandomWanderBehavior getRandomWanderBehavior() {
        return new RandomWanderBehavior(actionFactory, scenario);
    }
}
