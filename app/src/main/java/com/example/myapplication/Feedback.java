package com.example.myapplication;

public class Feedback {
    public long projectId;
    public String username;
    public String comment;

    public Feedback(long projectId, String username, String comment){
        this.projectId = projectId;
        this.username = username;
        this.comment = comment;
    }
}
