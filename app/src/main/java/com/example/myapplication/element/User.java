package com.example.myapplication.element;

public class User {
    public static String username;
    public String password;
    public int questionIndex;
    public String answer;

    public User(String username, String password, int questionIndex, String answer){
        this.username = username;
        this.password = password;
        this.questionIndex = questionIndex;
        this.answer = answer;
    }
}
