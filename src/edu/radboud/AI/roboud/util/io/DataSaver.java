package edu.radboud.ai.roboud.util.io;

import edu.radboud.ai.roboud.RoboudModel;
import org.dom4j.Element;

/**
 * Created by Pieter Marsman on 2-7-2014.
 */
public interface DataSaver {

    public void addElement(Element root, RoboudModel model);

    public void readElement(Element element, RoboudModel model);

}
