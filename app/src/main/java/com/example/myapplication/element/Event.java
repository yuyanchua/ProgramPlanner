package com.example.myapplication.element;

public class Event{
    public String eventId;
    public String eventTitle;
    public String eventDate;
    public boolean isNotify;

    public Event(String eventTitle, String eventDate, boolean isNotify){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.isNotify = isNotify;
    }

    public Event(String eventId, String eventTitle, String eventDate, boolean isNotify){
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.isNotify = isNotify;
    }

    public String toString() {
        return String.format("Event Title: %s, Event Date: %s\n", eventTitle, eventDate);
    }
}
