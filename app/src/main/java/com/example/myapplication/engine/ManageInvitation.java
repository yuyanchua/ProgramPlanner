package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.ViewInvitationActivity;
import com.example.myapplication.element.Invitation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageInvitation {
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    ViewInvitationActivity activity;
    String username;
    List<Invitation> invitationList;

    public ManageInvitation(ViewInvitationActivity activity, String username){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Users");
        this.username = username;
        this.activity = activity;

    }

    public List<Invitation> getInvitationList(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invitationList = new ArrayList<>();
                snapshot = snapshot.child(username).child("Invitation");
                for(DataSnapshot snap : snapshot.getChildren()){
                    String inviteId = snap.getKey();
                    String projectId = snap.child("projectId").getValue().toString();
                    String projectName = snap.child("projectName").getValue().toString();
                    String projectRole = snap.child("projectRole").getValue().toString();

                    Invitation tempInvite = new Invitation(projectId, projectName, projectRole);
                    invitationList.add(tempInvite);
                }

                activity.setupLayout(invitationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return invitationList;
    }
}
