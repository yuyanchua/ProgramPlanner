package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Task {
    public String task;
    public String taskId;
    public List<String> memberList;

    public Task(String task){
        this.task = task;
        this.memberList = new ArrayList<>();
    }

    public Task(String task, List<String> memberList){
        this.task = task;
        this.memberList = memberList;
    }
}
