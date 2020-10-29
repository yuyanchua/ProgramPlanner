package com.example.myapplication;

public class Event {
    public String eventTitle;
    public String eventDate;
    public boolean isNotify;

    public Event(String eventTitle, String eventDate, boolean isNotify){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.isNotify = isNotify;
    }
}
