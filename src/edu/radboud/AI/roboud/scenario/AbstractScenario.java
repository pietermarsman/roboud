package edu.radboud.ai.roboud.scenario;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public abstract class AbstractScenario implements Scenario {

    // Mobile phone
    protected boolean canTalk, canDrive, canListen, canSee, canLocate, canGoOnline;
    // Surrounding
    protected boolean interactingWithIndividual;
    protected Context context;

    public AbstractScenario(Context context) {
        canTalk = true;
        canListen = true;
        canDrive = true;
        interactingWithIndividual = true;
        canGoOnline = isOnline();
        this.context = context;
    }

    public AbstractScenario(Context context, boolean canSee, boolean canLocate, boolean canListen) {
        this(context);
        this.canSee = canSee;
        this.canLocate = canLocate;
        this.canListen = canListen;
    }

    public boolean isCanTalk() {
        return canTalk;
    }

    public boolean isCanDrive() {
        return canDrive;
    }

    public boolean isInteractingWithIndividual() {
        return interactingWithIndividual;
    }

    public boolean isCanListen() {
        return canListen;
    }

    public boolean isCanGoOnline() {
        return canGoOnline;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = (canTalk ? 1 : 0);
        result = 31 * result + (canDrive ? 1 : 0);
        result = 31 * result + (interactingWithIndividual ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractScenario)) return false;

        AbstractScenario that = (AbstractScenario) o;

        if (canDrive != that.canDrive) return false;
        if (canTalk != that.canTalk) return false;
        if (interactingWithIndividual != that.interactingWithIndividual) return false;
        if (canListen != that.isCanListen()) return false;
        if (canGoOnline != that.isCanGoOnline()) return false;

        return true;
    }

    @Override
    public String toString() {
        return "AbstractScenario{" +
                "canTalk=" + canTalk +
                ", canDrive=" + canDrive +
                ", interactingWithIndividual=" + interactingWithIndividual +
                ", canListen=" + canListen +
                ", canGoOnline=" + canGoOnline +
                '}';
    }
}
