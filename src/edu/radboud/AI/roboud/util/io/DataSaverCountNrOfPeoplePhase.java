package edu.radboud.ai.roboud.util.io;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.module.util.CountNrPeopleBehaviorPhase;
import org.dom4j.Element;

/**
 * Created by Pieter Marsman on 2-7-2014.
 * Adds and reads the CountNrOfPeoplePhase to a xml document
 */
public class DataSaverCountNrOfPeoplePhase implements DataSaver {

    public static final String COUNT_NR_OF_PEOPLE = "countNrOfPeople";
    private static final String TAG = "DataSaverCountNrOfPeople";

    @Override
    public void addElement(Element root, RoboudModel model) {
        Element addCountNrOfPeople = root.addElement(COUNT_NR_OF_PEOPLE);
        if (model.getCountNrPeopleBehaviorPhase() != null)
            addCountNrOfPeople.addText(model.getCountNrPeopleBehaviorPhase().toString());
        else {
            addCountNrOfPeople.addText("null");
        }
    }

    @Override
    public void readElement(Element element, RoboudModel model) {
        if (element.getName().equals(COUNT_NR_OF_PEOPLE)) {
            String value = element.getText();
            if (!value.equals("null"))
                model.setCountNrPeopleBehaviorPhase(CountNrPeopleBehaviorPhase.valueOf(value));
            else
                model.setCountNrPeopleBehaviorPhase(null);
            Log.d(TAG, "This element is of type " + COUNT_NR_OF_PEOPLE);
        } else {
            Log.v(TAG, "This element is not of type " + COUNT_NR_OF_PEOPLE);
        }
    }
}
