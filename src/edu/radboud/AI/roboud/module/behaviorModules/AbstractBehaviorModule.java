package edu.radboud.ai.roboud.module.behaviorModules;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.behaviour.behaviors.AbstractBehavior;
import edu.radboud.ai.roboud.behaviour.BehaviorFactory;
import edu.radboud.ai.roboud.module.Module;
import edu.radboud.ai.roboud.util.Scenario;

import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Mike Ligthart on 28-6-2014.
 */
public abstract class AbstractBehaviorModule extends Observable implements Observer, Module, Runnable {

    public static final String TAG = "AbstractBehaviorModule";

    private boolean running;
    private Thread moduleThread;

    protected BehaviorFactory behaviorFactory;
    protected AbstractBehavior currentBehavior;
    protected boolean behaviorReady;

    public AbstractBehaviorModule(RoboudController controller, Scenario scenario){
        running = false;
        behaviorFactory = BehaviorFactory.getInstance(scenario, controller);
    }

    protected abstract AbstractBehavior firstBehavior();

    public void startRunning(){
        if (!running){
            running = true;
            currentBehavior = firstBehavior();
            behaviorReady = true;
            moduleThread = new Thread(this);
            moduleThread.start();
        }
        else{
            Log.w(TAG, "Module is already running");
        }

    }

    public void stopRunning(){
        currentBehavior.deleteObserver(this);
        running = false;
        moduleThread.interrupt();
        moduleThread = null;
        //TODO save information??.
    }

    public boolean isRunning() {
        return running;
    }

    protected void finished(){
        running = false;
        setChanged();
        notifyObservers();
    }

    @Override
    public void run(){
        Log.i(TAG, "thread is started");
        while (running){
            if (behaviorReady && moduleThread != null ){
                behaviorReady = false;
                Log.i(TAG, "executing behavior " + currentBehavior.getClass().getSimpleName());
                currentBehavior.executeBehaviour();
            }
            else{

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "InterruptedException in module is thrown", e);
                }
            }
        }
    }
}
