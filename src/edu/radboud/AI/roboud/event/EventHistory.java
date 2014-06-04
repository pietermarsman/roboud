package edu.radboud.ai.roboud.event;


import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Gebruiker on 23-5-2014.
 */
public class EventHistory extends Observable {

    private LinkedList<Event> events;

    public EventHistory() {
        events = new LinkedList<Event>();
        Event event = new Event(EventType.NEW_EVENT_HISTORY);
        events.add(event);
        this.setChanged();
        this.notifyObservers(event);
    }

    public void newEvent(Event e) {
        events.add(e);
        this.setChanged();
        this.notifyObservers(e);
    }

    public List<Event> getLastEventsOfType(EventType eventType, int lastN) {
        List<Event> lastEvents = new LinkedList<Event>();
        for (Event e : events)
            if (e.getEventType() == eventType) {
                lastEvents.add(e);
                if (lastEvents.size() >= lastN)
                    break;
            }
        return lastEvents;
    }
}
