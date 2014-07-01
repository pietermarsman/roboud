package edu.radboud.ai.roboud.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Observable;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class Scenario extends Observable {

    private static final String TAG = "AbstractScenario";
    //Debug
    private boolean debugMode;

    //Android Phone
    private boolean canTalk, canListen, canSee, canLocate, canGoOnline;

    //RoboMe
    private boolean canWander, canTurn;

    //Context
    private Context context;

    public Scenario(Context context) {
        //Debug
        debugMode = false;

        //Android Phone
        canTalk = true;
        canListen = true;
        canSee = true;
        canLocate = true;
        canGoOnline = true;
        //canGoOnline = isOnline();

        //RoboMe
        canWander = true;
        canTurn = true;

        // Surrounding
        this.context = context;
    }

    public Scenario(Context context, boolean debugMode, boolean canTalk, boolean canListen, boolean canSee, boolean canLocate) {
        this(context);
        this.debugMode = debugMode;
        this.canTalk = canTalk;
        this.canListen = canListen;
        this.canSee = canSee;
        this.canLocate = canLocate;
    }

    public Scenario(Context context, boolean debugMode, boolean canTalk, boolean canListen, boolean canSee, boolean canLocate, boolean canWander, boolean canTurn) {
        this(context);
        this.debugMode = debugMode;
        this.canTalk = canTalk;
        this.canListen = canListen;
        this.canSee = canSee;
        this.canLocate = canLocate;
        this.canWander = canWander;
        this.canTurn = canTurn;
    }

    //Debug
    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        setChanged();
        notifyObservers();
    }


    //Android Phone
    public boolean isCanTalk() {
        return canTalk;
    }

    public void setCanTalk(boolean canTalk) {
        this.canTalk = canTalk;
        setChanged();
        notifyObservers();
    }

    public boolean isCanListen() {
        return canListen;
    }

    public void setCanListen(boolean canListen) {
        this.canListen = canListen;
        setChanged();
        notifyObservers();
    }

    public boolean isCanSee() {
        return canSee;
    }

    public void setCanSee(boolean canSee) {
        this.canSee = canSee;
        setChanged();
        notifyObservers();
    }

    public boolean isCanGoOnline() {
        return canGoOnline;
    }

    public void setCanGoOnline(boolean canGoOnline) {
        this.canGoOnline = canGoOnline;
        setChanged();
        notifyObservers();
    }

    public boolean isCanLocate() {
        return canLocate;
    }

    public void setCanLocate(boolean canLocate) {
        this.canLocate = canLocate;
        setChanged();
        notifyObservers();
    }

    //RoboMe
    public boolean isCanWander() {
        return canWander;
    }

    public void setCanWander(boolean canWander) {
        this.canWander = canWander;
        setChanged();
        notifyObservers();
    }

    public boolean isCanTurn() {
        return canTurn;
    }

    public void setCanTurn(boolean canTurn) {
        this.canTurn = canTurn;
        setChanged();
        notifyObservers();
    }

    //Context
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scenario)) return false;

        Scenario scenario = (Scenario) o;

        if (canGoOnline != scenario.canGoOnline) return false;
        if (canListen != scenario.canListen) return false;
        if (canLocate != scenario.canLocate) return false;
        if (canSee != scenario.canSee) return false;
        if (canTalk != scenario.canTalk) return false;
        if (canTurn != scenario.canTurn) return false;
        if (canWander != scenario.canWander) return false;
        if (debugMode != scenario.debugMode) return false;
        if (context != null ? !context.equals(scenario.context) : scenario.context != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (debugMode ? 1 : 0);
        result = 31 * result + (canTalk ? 1 : 0);
        result = 31 * result + (canListen ? 1 : 0);
        result = 31 * result + (canSee ? 1 : 0);
        result = 31 * result + (canLocate ? 1 : 0);
        result = 31 * result + (canGoOnline ? 1 : 0);
        result = 31 * result + (canWander ? 1 : 0);
        result = 31 * result + (canTurn ? 1 : 0);
        result = 31 * result + (context != null ? context.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "debugMode=" + debugMode +
                ", canTalk=" + canTalk +
                ", canListen=" + canListen +
                ", canSee=" + canSee +
                ", canLocate=" + canLocate +
                ", canGoOnline=" + canGoOnline +
                ", canWander=" + canWander +
                ", canTurn=" + canTurn +
                ", context=" + context +
                '}';
    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
