package com.example.myapplication;

import com.google.firebase.database.Exclude;

public class Image {
    public String ImageUrl;
    //private String mKey;

    public Image(String ImageUrl){
        this.ImageUrl =  ImageUrl;
    }

    /*@Exclude
    public String getmKey(){
        return mKey;
    }

    @Exclude
    public void setKey(String key){
        mKey = key;
    }*/
    public Image(){}
}
