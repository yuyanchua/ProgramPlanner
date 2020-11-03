package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.InviteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageInvite {
    FirebaseDatabase firebase;
    DatabaseReference db_ref;
    InviteActivity activity;

    public ManageInvite(InviteActivity activity, String projectId){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(projectId);
        this.activity = activity;
    }

    public void getInviteCode(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String clientCode = snapshot.child("clientCode").getValue().toString();
                String devCode = snapshot.child("devCode").getValue().toString();

                activity.setupCode(clientCode, devCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
