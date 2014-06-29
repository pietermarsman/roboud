package edu.radboud.ai.roboud.behaviour;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.action.ActionFactory;
import edu.radboud.ai.roboud.behaviour.behaviors.*;
import edu.radboud.ai.roboud.util.Scenario;
import edu.radboud.ai.roboud.task.TaskFactory;

/**
 * Created by Pieter Marsman on 19-6-2014.
 */
public class BehaviorFactory {


    private final static String TAG = "BehaviorFactory";
    private static BehaviorFactory instance = null;
    private ActionFactory actionFactory;
    private TaskFactory taskFactory;

    private BehaviorFactory(Scenario scenario, RoboudController controller) {
        actionFactory = ActionFactory.getInstance(controller);
        taskFactory = TaskFactory.getInstance(scenario, controller);
    }

    public synchronized static BehaviorFactory getInstance(Scenario scenario, RoboudController controller) {
        if (instance == null || !instance.equals(scenario))
            instance = new BehaviorFactory(scenario, controller);
        return instance;
    }

    public TestBehavior getTestBehavior() {
        return new TestBehavior(actionFactory, taskFactory);
    }

    public IntroduceBehavior getIntroduceBehavior(){ return new IntroduceBehavior(actionFactory, taskFactory);}

    public AreWeFamiliarBehavior getAreWeFamiliarBehavior(){return  new AreWeFamiliarBehavior(actionFactory, taskFactory);}
    
    public SelectExistingUserBehavior getExistingUserBehavior(){return  new SelectExistingUserBehavior(actionFactory, taskFactory);}

    public SettingsBehavior getSettingsBehavior() {return new SettingsBehavior(actionFactory, taskFactory);}

    public TurnMeOffBehavior getTurnMeOffBehavior() {return new TurnMeOffBehavior(actionFactory, taskFactory);}

    public RandomWanderBehavior getRandomWanderBehavior() {return new RandomWanderBehavior(actionFactory, taskFactory);}
}
