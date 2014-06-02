package edu.radboud.ai.roboud.event;

/**
 * Created by Gebruiker on 23-5-2014.
 */
public class Event {

    private EventType eventType;
    private String eventDescription;
    private long eventTime;
    private int priority;

    public Event(EventType eventType, long eventTime, String eventDescription, int priority) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventTime = eventTime;
        this.priority = priority;
    }

    public Event(EventType eventType) {
        this(eventType, System.currentTimeMillis(), "", 0);
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public long getEventTime() {
        return eventTime;
    }

    public int getPriority() {
        return priority;
    }
}
