package com.example.myapplication.element;

public class Roles {
    public String projectId;
    public String projectName;
    public String roles;
    public String username;

    public Roles(String projectId, String projectName, String roles){
        this.projectId = projectId;
        this.projectName = projectName;
        this.roles = roles;
    }

    public Roles(String username, String roles){
        this.username = username;
        this.roles = roles;
    }

    public String toString(){
        return String.format("%s : %s", username, roles);

    }

}
