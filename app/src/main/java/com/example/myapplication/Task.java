package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Task {
    public String task;
    public String taskId;
    public List<String> memberList;

    public Task(String taskName){
        this.task = taskName;
        this.memberList = new ArrayList<>();
    }

    public Task(String taskName, List<String> memberList){
        this.task = taskName;
        this.memberList = memberList;
    }
}
