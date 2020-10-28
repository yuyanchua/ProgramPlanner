package com.example.myapplication;

import java.util.Random;

public class Project {
    public static long projectId;
    public static String projectName;
    public static String clientCode;
    public static String devCode;

    public Project(long projectId, String projectName, String clientCode, String devCode){
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientCode = clientCode;
        this.devCode = devCode;
    }

    public static String generateCode(boolean isClient){
        Random random = new Random();
        String code = "";
        for(int i = 0; i < 6; i ++)
            code += random.nextInt(10);

        int code_int = Integer.parseInt(code);
        if(isClient){
            if(code_int % 2 != 1)
                code = Integer.toString(code_int + 1);
        }else{
            if(code_int % 2 != 0)
                code = Integer.toString(code_int + 1);
        }

        if(code.length() > 6){
            int diff = 6 - code.length();
            while(diff > 0 ){
                code =  "0" + code;
                diff --;
            }
        }
        return code;
    }

    public String getProjectName(){
        return this.projectName;
    }

    public String getClientCode(){
        return this.clientCode;
    }

    public String getDevCode(){
        return this.devCode;
    }

    public long getProjectId(){
        return this.projectId;
    }

    public String toString(){
        return "Project Id: " + projectId;
    }

}
