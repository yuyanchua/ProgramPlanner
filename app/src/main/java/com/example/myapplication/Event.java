package com.example.myapplication;

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

    /*
    //Removed by Henry Koenig after notified that this method was not needed.
    public boolean equals(Event event){
        boolean isEqual = false;
        if(this.eventDate.equals(event.eventDate)){
            isEqual = true;
        }
        return false;
    }
    */
    public String toString() {
        return String.format("Event Title: %s, Event Date: %s\n", eventTitle, eventDate);
    }
}
