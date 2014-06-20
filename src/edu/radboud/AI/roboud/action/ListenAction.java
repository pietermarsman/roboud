package edu.radboud.ai.roboud.action;

import android.util.Log;
import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.event.Event;
import edu.radboud.ai.roboud.event.EventType;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class ListenAction extends AbstractAction implements Observer {

    private static final String TAG = "edu.radboud.ai.roboud.action.ListenAction";
    private String result;

    public ListenAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Observer abstractBehaviour) {
        addObserver(abstractBehaviour);
        controller.listenToSpeech(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i(TAG, "Speech data received. About to check if it make sense");
        if (data instanceof Event) {
            Event e = (Event) data;
            if (e.getEventType() == EventType.NEW_SPEECH_DATA) {
                result = controller.getModel().getVoiceResults().get(0);
                Log.i(TAG, "Speech received: " + result);
                setChanged();
                notifyObservers(result);
            } else {
                Log.i(TAG, "No speech data");
            }
        }
    }

    public String getResult() {
        return result;
    }
}
