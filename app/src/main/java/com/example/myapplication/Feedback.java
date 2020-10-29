package com.example.myapplication;

public class Feedback {
//    public long projectId;
    public String username;
    public String comment;

    public Feedback(){
        username = "";
        comment = "";
    }

    public Feedback(String username, String comment){
//        this.projectId = projectId;
        this.username = username;
        this.comment = comment;
    }

    public String getUsername(){
        return username;
    }

    public String getComment(){
        return this.comment;
    }
}
