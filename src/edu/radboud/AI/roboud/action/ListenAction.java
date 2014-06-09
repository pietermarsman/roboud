package edu.radboud.ai.roboud.action;

import edu.radboud.ai.roboud.RoboudController;
import edu.radboud.ai.roboud.event.Event;
import edu.radboud.ai.roboud.event.EventType;
import edu.radboud.ai.roboud.scenario.Scenario;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Pieter Marsman on 2-6-2014.
 */
public class ListenAction extends AbstractAction implements Observer {

    private String result;

    public ListenAction(RoboudController controller) {
        super(controller);
    }

    @Override
    public void doActions(Scenario scenario, Observer abstractBehaviour) {
        addObserver(abstractBehaviour);
        controller.listenToSpeech(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof Event) {
            Event e = (Event) data;
            if (e.getEventType() == EventType.NEW_SPEECH_DATA) {
                result = controller.getModel().getVoiceResults().get(0);
                setChanged();
                notifyObservers();
            }
        }
    }
}
