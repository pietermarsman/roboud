package edu.radboud.ai.roboud.scenario;

import android.content.Context;

/**
 * Created by Pieter Marsman on 24-5-2014.
 */
public class BarScenario extends AbstractScenario {

    public BarScenario(Context context) {
        super(context);
        interactingWithIndividual = false;
        canDrive = false;
    }
}
