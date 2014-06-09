package edu.radboud.ai.roboud.event;

/**
 * Created by Gebruiker on 23-5-2014.
 */
public class Event {

    private EventType eventType;
    private Object data;
    private String eventDescription;
    private long eventTime;
    private int level;

    public Event(EventType eventType, Object data, long eventTime, String eventDescription, int level) {
        this.eventType = eventType;
        this.data = data;
        this.eventDescription = eventDescription;
        this.eventTime = eventTime;
        this.level = level;
    }

    public Event(EventType eventType, Object data) {
        this(eventType, data, System.currentTimeMillis(), "", 0);
    }

    public Event(EventType eventType) {
        this(eventType, null);
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

    public int getLevel() {
        return level;
    }
}
