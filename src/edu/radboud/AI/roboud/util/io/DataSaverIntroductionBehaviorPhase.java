package edu.radboud.ai.roboud.util.io;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.module.util.IntroductionBehaviorPhase;
import org.dom4j.Element;

/**
 * Created by Pieter Marsman on 2-7-2014.
 * Adds and reads the CountNrOfPeoplePhase to a xml document
 */
public class DataSaverIntroductionBehaviorPhase implements DataSaver {

    public static final String INTRODUCTION_BEHAVIOR_PHASE = "introductionBehaviorPhase";
    private static final String TAG = "DataSaverIntroductionBehaviorPhase";

    @Override
    public void addElement(Element root, RoboudModel model) {
        Element addIntroductionBehaviorPhase = root.addElement(INTRODUCTION_BEHAVIOR_PHASE);
        if (model.getIntroductionBehaviorPhase() != null)
            addIntroductionBehaviorPhase.addText(model.getIntroductionBehaviorPhase().toString());
        else {
            addIntroductionBehaviorPhase.addText("null");
        }
    }

    @Override
    public void readElement(Element element, RoboudModel model) {
        if (element.getName().equals(INTRODUCTION_BEHAVIOR_PHASE)) {
            String value = element.getText();
            if (!value.equals("null"))
                model.setIntroductionBehaviorPhase(IntroductionBehaviorPhase.valueOf(value));
            else
                model.setCountNrPeopleBehaviorPhase(null);
            Log.d(TAG, "This element is of type " + INTRODUCTION_BEHAVIOR_PHASE);
        } else {
            Log.v(TAG, "This element is not of type " + INTRODUCTION_BEHAVIOR_PHASE);
        }
    }
}
