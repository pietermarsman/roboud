package edu.radboud.ai.roboud.module.behaviorModules;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.behaviour.behaviors.*;
import edu.radboud.ai.roboud.module.util.IntroductionBehaviorPhase;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.Observable;

/**
 * Created by mikel_000 on 28-6-2014.
 */
public class IntroductionBehaviorModule extends AbstractBehaviorModule {

    public static final String TAG = "IntroductionBehaviorModule";
    private static IntroductionBehaviorModule ourInstance = null;

    private IntroductionBehaviorPhase phase;

    public synchronized static IntroductionBehaviorModule getInstance(RoboudController controller, Scenario scenario) {
        if (ourInstance == null)
            ourInstance = new IntroductionBehaviorModule(controller, scenario);
        return ourInstance;
    }

    private IntroductionBehaviorModule(RoboudController controller, Scenario scenario) {
        super(controller, scenario);
    }

    @Override
    protected AbstractBehavior firstBehavior() {
        phase = IntroductionBehaviorPhase.KNOWNUSER;
        AreWeFamiliarBehavior firstBehavior = behaviorFactory.getAreWeFamiliarBehavior();
        firstBehavior.addObserver(this);
        return firstBehavior;
    }

    @Override
    protected void stopBehavior() {
        if (currentBehavior != null){
            currentBehavior.deleteObserver(this);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.i(TAG, "==Introduction Module is updated== by " + observable.getClass().getSimpleName() + " and the phase is at " + phase);
        if (phase == IntroductionBehaviorPhase.KNOWNUSER){
            if (observable instanceof AreWeFamiliarBehavior) {
                AreWeFamiliarBehavior previousBehavior = (AreWeFamiliarBehavior) observable;
                previousBehavior.deleteObserver(this);
                if (previousBehavior.isFamiliar()){
                    phase = IntroductionBehaviorPhase.SELECTEXISTINGUSER;
                    Log.i(TAG, "Phase is set to SELECTEXISTINGUSER");
                    currentBehavior = behaviorFactory.getExistingUserBehavior();
                    currentBehavior.addObserver(this);
                    behaviorReady = true;
                }
                else{
                    phase = IntroductionBehaviorPhase.NEWUSER;
                    Log.i(TAG, "Phase is set to NEWUSER");
                    currentBehavior = behaviorFactory.getIntroduceBehavior();
                    currentBehavior.addObserver(this);
                    behaviorReady = true;
                }
            }
        }
        if (phase == IntroductionBehaviorPhase.SELECTEXISTINGUSER || phase == IntroductionBehaviorPhase.NEWUSER){
            if (observable instanceof SelectExistingUserBehavior || observable instanceof IntroduceBehavior){
                AbstractBehavior previousBehavior = (AbstractBehavior) observable;
                previousBehavior.deleteObserver(this);
                phase = IntroductionBehaviorPhase.GETSETTINGS;
                Log.i(TAG, "Phase is set to GETSETTINGS");
                currentBehavior = behaviorFactory.getSettingsBehavior();
                currentBehavior.addObserver(this
                );
                behaviorReady = true;
            }
        }
        if (phase == IntroductionBehaviorPhase.GETSETTINGS){
            if (observable instanceof SettingsBehavior){
                SettingsBehavior previousBehavior = (SettingsBehavior) observable;
                previousBehavior.deleteObserver(this);
                Log.i(TAG, "and we're finished");
                finished();
            }


        }
    }

    public IntroductionBehaviorPhase getPhase(){
        return phase;
    }
}
