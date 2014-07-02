package edu.radboud.ai.roboud.util.io;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudModel;
import org.dom4j.Element;

/**
 * Created by Pieter Marsman on 2-7-2014.
 * Adds and reads the NumberOfTimesStarted variable to a xml file
 */
public class DataSaverIncrement implements DataSaver {

    public static final String INCREMENT = "increment";
    private static final String TAG = "DataSaverIncrement";

    @Override
    public void addElement(Element root, RoboudModel model) {
        root.addElement(INCREMENT).setText(String.valueOf(model.getNumberOfTimesStarted()));
    }

    @Override
    public void readElement(Element element, RoboudModel model) {
        if (element.getName().equals(INCREMENT)) {
            int value = Integer.parseInt(element.getText());
            model.setNumberOfTimesStarted(value + 1);
            Log.d(TAG, "Increment value read. It is now " + model.getNumberOfTimesStarted());
        } else {
            Log.v(TAG, "This element is not of type " + INCREMENT);
        }
    }
}
