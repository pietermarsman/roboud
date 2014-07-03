package edu.radboud.ai.roboud.util.io;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Guido on 25-06-14.
 */
public class DataWriter {
    public static final String TAG = "DataWriter";

    private List<DataSaver> dataSavers;

    public DataWriter() {
        dataSavers = new LinkedList<DataSaver>();
        dataSavers.add(new DataSaverCountNrOfPeoplePhase());
        dataSavers.add(new DataSaverIncrement());
        dataSavers.add(new DataSaverIntroductionBehaviorPhase());
        dataSavers.add(new DataSaverUsers());
    }

    /**
     * Write information to an xml file. This methods uses the DataSavers that are added in the constructor to save
     * things in an xml file.
     *
     * @param file
     * @param model
     */
    public void writeToFile(File file, RoboudModel model) {
        try {
            Document d = createDocument(model);
            FileWriter out = new FileWriter(file);
            d.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the xml file and apply all the DataSavers to it. This means that the DataSavers change the model using the
     * information from the xml file.
     *
     * @param file
     * @param model
     */
    public void readFromFile(File file, RoboudModel model) {
        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            StringBuffer sb = new StringBuffer();
//            while(br.read()!=-1)
//            {
//                sb.append(br.readLine());
//            }
//            Log.i(TAG, "File content: " + sb.toString());
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Log.i(TAG, document.asXML());

            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                for (DataSaver ds : dataSavers) {
                    ds.readElement(element, model);
                }
            }
            Log.i(TAG, "Model was changed by reading an xml file: " + model);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * A document is created. The document consists of Elements which are created by a DataSaver.
     *
     * @param model
     * @return
     */
    private Document createDocument(RoboudModel model) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        for (DataSaver ds : dataSavers) {
            ds.addElement(root, model);
        }

        Log.i(TAG, "Xml created: " + document.asXML());
        return document;
    }

}
