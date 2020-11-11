package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.element.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUser {
    FirebaseDatabase firebase;
    DatabaseReference db_ref;
    List<String> userList;

    public SearchUser(){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Users");
        retrieveData();
    }

    private void retrieveData(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = new ArrayList<>();

                for(DataSnapshot snap: snapshot.getChildren()){
                    String username = snap.getKey();
                    userList.add(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<String> searchUserByName(String keyword){
        List<String> resultList = new ArrayList<>();

        for(String username : userList){
            if(username.contains(keyword))
                userList.add(username);
        }

        return resultList;
    }
}
