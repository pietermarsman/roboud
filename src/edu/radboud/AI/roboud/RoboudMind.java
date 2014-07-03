package edu.radboud.ai.roboud;

import android.util.Log;
import edu.radboud.ai.roboud.module.behaviorModules.AbstractBehaviorModule;
import edu.radboud.ai.roboud.module.behaviorModules.CountNrPeopleBehaviorModule;
import edu.radboud.ai.roboud.module.behaviorModules.IntroductionBehaviorModule;
import edu.radboud.ai.roboud.module.behaviorModules.TurnMeOffBehaviorModule;
import edu.radboud.ai.roboud.module.functionModules.AbstractFunctionModule;
import edu.radboud.ai.roboud.module.functionModules.ConnectedFunctionModule;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.*;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class RoboudMind implements Observer {

    public static final String TAG = "RoboudMind";

    private static RoboudMind instance = null;

    private RoboudModel model;
    private RoboudController controller;
    private Scenario scenario;

    private List<AbstractFunctionModule> functionModules;
    private AbstractBehaviorModule behaviorModule;

    private boolean running;

    private RoboudMind(RoboudController controller, Scenario scenario) {
        this.controller = controller;
        model = controller.getModel();
        this.scenario = scenario;
        running = false;

        //The first module
        behaviorModule = IntroductionBehaviorModule.getInstance(controller, scenario);
        behaviorModule.addObserver(this);

        //Add all necessary function modules
        functionModules = new LinkedList<AbstractFunctionModule>();
        functionModules.add(ConnectedFunctionModule.getInstance(controller));
    }

    public static synchronized RoboudMind getInstance(RoboudController controller, Scenario scenario) {
        if (instance == null)
            instance = new RoboudMind(controller, scenario);
        return instance;
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i(TAG, "==Mind is updated== by " + observable.getClass().getSimpleName());
        Log.i(TAG, "model headPhoneConnected = " + model.isRobomeHeadsetPluggedIn());
        if(observable instanceof CountNrPeopleBehaviorModule){
            CountNrPeopleBehaviorModule oldModule = (CountNrPeopleBehaviorModule) observable;
            Log.i(TAG, "Updated by CountNrPeopleBehaviorModule that is in phase: " + oldModule.getPhase());
            oldModule.deleteObserver(this);
            oldModule.stopRunning();

            behaviorModule = TurnMeOffBehaviorModule.getInstance(controller, scenario);
            behaviorModule.addObserver(this);
            behaviorModule.startRunning();
        }
        else if (observable instanceof IntroductionBehaviorModule) {
            IntroductionBehaviorModule oldModule = (IntroductionBehaviorModule) observable;
            oldModule.deleteObserver(this);
            oldModule.stopRunning();

            behaviorModule = CountNrPeopleBehaviorModule.getInstance(controller, scenario);
            behaviorModule.addObserver(this);
            behaviorModule.startRunning();
        } else if (observable instanceof ConnectedFunctionModule) {
            ConnectedFunctionModule connectedFunctionModule = (ConnectedFunctionModule) observable;

            if (!connectedFunctionModule.getConnected()) {
                behaviorModule.stopRunning();
                behaviorModule.deleteObserver(this);
            } else {
                if (!behaviorModule.isRunning()) {
                    Log.i(TAG, "behavior module running = " + behaviorModule.isRunning());
                    behaviorModule.addObserver(this);
                    behaviorModule.startRunning();
                }
            }
        }
    }

    public void stopRunning() {
        running = false;
        for (Iterator<AbstractFunctionModule> it = functionModules.iterator(); it.hasNext(); ) {
            AbstractFunctionModule functionModule = it.next();
            functionModule.deleteObserver(this);
            functionModule.stopRunning();
        }
        behaviorModule.deleteObserver(this);
        behaviorModule.stopRunning();
    }

    public void startRunning() {
        Log.d(TAG, "Start running roboud mind");
        if (!running) {
            running = true;
            for (Iterator<AbstractFunctionModule> it = functionModules.iterator(); it.hasNext(); ) {
                AbstractFunctionModule functionModule = it.next();
                functionModule.addObserver(this);
                functionModule.startRunning();
            }
            if (model.isRobomeHeadsetPluggedIn()) {
                behaviorModule.addObserver(this);
                behaviorModule.startRunning();
            }
        } else {
            Log.w(TAG, "Already running, no need to start it again");
        }
    }

    public boolean isRunning() {
        return running;
    }

}
