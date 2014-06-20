package edu.radboud.ai.roboud.scenario;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public abstract class AbstractScenario implements Scenario {

    protected boolean canTalk, canDrive, interactingWithIndividual, canListen, hasInternet;

    public AbstractScenario() {
        canTalk = true;
        canListen = true;
        canDrive = true;
        interactingWithIndividual = true;
        hasInternet = true;
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

    public boolean isHasInternet() {
        return hasInternet;
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
        if (hasInternet != that.isHasInternet()) return false;

        return true;
    }

    @Override
    public String toString() {
        return "AbstractScenario{" +
                "canTalk=" + canTalk +
                ", canDrive=" + canDrive +
                ", interactingWithIndividual=" + interactingWithIndividual +
                ", canListen=" + canListen +
                ", hasInternet=" + hasInternet +
                '}';
    }
}
