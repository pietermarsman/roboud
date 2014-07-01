package edu.radboud.ai.roboud.module.functionModules;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.RoboudModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mikel_000 on 29-6-2014.
 */
public class ConnectedFunctionModule extends AbstractFunctionModule implements Observer {

    private static ConnectedFunctionModule ourInstance = null;
    private RoboudModel model;
    private RoboudController controller;
    private boolean connected, running;

    private ConnectedFunctionModule(RoboudController controller) {
        this.controller = controller;
        model = controller.getModel();
        running = false;
    }

    public synchronized static ConnectedFunctionModule getInstance(RoboudController controller) {
        if (ourInstance == null)
            ourInstance = new ConnectedFunctionModule(controller);
        return ourInstance;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof RoboudModel) {
            boolean newConnectInfo = model.isRobomeHeadsetPluggedIn();
            if (connected != newConnectInfo) {
                connected = newConnectInfo;
                if (connected) {
                    controller.appInConnectedMode();
                    setChanged();
                    notifyObservers();
                } else {
                    controller.appInDisconnectedMode();
                    setChanged();
                    notifyObservers();
                }
            }
        }
    }

    @Override
    public void startRunning() {
        running = true;
        model.addObserver(this);
        connected = model.isRobomeHeadsetPluggedIn();
        if (connected) {
            controller.appInConnectedMode();
        } else {
            controller.appInDisconnectedMode();
        }
    }

    @Override
    public void stopRunning() {
        model.deleteObserver(this);
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public boolean getConnected() {
        return connected;
    }
}
