package com.example.myapplication.element;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class Task {
    public String task;
    public String taskId;
    public List<String> memberList;

    public Task(String taskId, String taskName){
        this.taskId = taskId;
        this.task = taskName;
        this.memberList = new ArrayList<>();
    }

    public Task(String taskName, List<String> memberList){
        this.task = taskName;
        this.memberList = memberList;
    }

    public Task(String taskId, String taskName, List<String> memberList){
        this.taskId = taskId;
        this.task = taskName;
        this.memberList = memberList;
    }

    @Override
    public String toString(){
//        String output = String.format("Task: %s\n, TaskId: %s,\n", task, taskId);

        String memberStr = "";
        for(String member : memberList)
            memberStr += member + ", ";
        String output = String.format("%s: %s\n(%s)", taskId, task, memberStr);
        return output;
    }
}
