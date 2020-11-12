package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.ViewApplicationActivity;
import com.example.myapplication.element.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageApplication {
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    ViewApplicationActivity activity;
    String projectId;
    List<Application> applicationList;

    public ManageApplication(ViewApplicationActivity activity, String projectId){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project");

        this.projectId = projectId;
        this.activity = activity;
    }

    public List<Application> getApplicationList(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicationList = new ArrayList<>();
                snapshot = snapshot.child(projectId).child("Application");
                for (DataSnapshot snap : snapshot.getChildren()){
                    String applicationId = snap.getKey();
                    String username = snap.child("username").getValue().toString();
                    String roles = snap.child("roles").getValue().toString();

                    Application tempApp = new Application(username, roles);
                    applicationList.add(tempApp);
                }

                activity.setupLayout(applicationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return applicationList;
    }
}
