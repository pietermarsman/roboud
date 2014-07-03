package edu.radboud.ai.roboud.util.io;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudModel;
import edu.radboud.ai.roboud.module.util.RoboudUser;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by Pieter Marsman on 2-7-2014.
 * Adds and reads the CountNrOfPeoplePhase to a xml document
 */
public class DataSaverUsers implements DataSaver {

    public static final String USERS = "users",
            USER_ELEMENT = "user",
            USER_NAME = "username",
            USER_AGE = "userage",
            USER_SEX = "usersex";
    private static final String TAG = "DataSaverUsers";

    @Override
    public void addElement(Element root, RoboudModel model) {
        Element addUser = root.addElement(USERS);
        if (model.getUserNames() != null)
            for (String username : model.getUserNames()) {
                RoboudUser user = model.getUser(username);
                Element userElement = addUser.addElement(USER_ELEMENT);
                userElement.addAttribute(USER_NAME, user.getName());
                userElement.addAttribute(USER_AGE, String.valueOf(user.age));
                userElement.addAttribute(USER_SEX, String.valueOf(user.isMan));
            }
        else {
            addUser.addText("null");
        }
    }

    @Override
    public void readElement(Element root, RoboudModel model) {
        if (root.getName().equals(USERS)) {
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                Log.d(TAG, "Child element is of type " + element.getName());
                if (element.getName().equals(USER_ELEMENT)) {
                    Attribute name = element.attribute(USER_NAME);
                    Attribute age = element.attribute(USER_AGE);
                    Attribute sex = element.attribute(USER_SEX);
                    RoboudUser user = new RoboudUser(name.getValue());
                    String ageString = age.getValue();
                    if (!ageString.equals("null"))
                        user.age = Integer.parseInt(ageString);
                    String sexString = age.getValue();
                    if (!sexString.equals("null"))
                        user.isMan = Boolean.parseBoolean(sexString);
                    model.addUser(user);
                    Log.d(TAG, "New user " + user);
                }
            }
            Log.d(TAG, "This element is of type " + USERS);
        } else {
            Log.v(TAG, "This element is not of type " + USERS);
        }
    }
}
