package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.InviteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageInvite {
    FirebaseDatabase firebase;
    DatabaseReference db_ref_project, db_ref_roles, db_ref_users;
    InviteActivity activity;
    List<String> userList;
    List<String> projectMemberList;
    String projectId;


    public ManageInvite(InviteActivity activity, String projectId){
        firebase = FirebaseDatabase.getInstance();
        db_ref_project = firebase.getReference("Project").child(projectId);
        db_ref_roles = firebase.getReference("Roles").child(projectId);
        db_ref_users = firebase.getReference("Users");

        retrieveUserData();
        retrieveMemberList();
        this.activity = activity;
        this.projectId = projectId;
    }

    public void inviteUser(String username, String projectName, String projectRoles){

        if(username.isEmpty())
            activity.setErrText("Please enter a username");

//        if(!isUserExist(username)){
//            //return error
//            activity.setErrText("The user entered does not exists");
//            return;
//        }
//        if(isMember(username)){
//            activity.setErrText("The user entered is already in project");
//        }

        if(isUserExist(username) && !isMember(username))
            sendInvite(username, projectName, projectRoles);

    }

    public boolean isUserExist(String username){
        for(String temp : userList){
            if(temp.equals(username)){
                return true;
            }
        }
        activity.setErrText("The user entered does not exists");
        return false;
    }

    public boolean isMember(String username){
        for(String member : projectMemberList){
            if(member.equals(username)){
                return true;
            }
        }

        activity.setErrText("The user entered is already in project");
        return false;
    }

    private void retrieveUserData(){
        db_ref_users.addValueEventListener(new ValueEventListener() {
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

    private void retrieveMemberList(){
        db_ref_roles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                projectMemberList = new ArrayList<>();
                for(DataSnapshot snap : snapshot.getChildren()){
                    String member = snap.getKey();
                    boolean isMember = snap.child("Roles").exists();

                    if(isMember){
                        projectMemberList.add(member);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendInvite(String username, String projectName, String projectRoles){
        final String user = username;
        final String name = projectName;
        final String roles = projectRoles;
        System.out.println("Send Invite here");
        db_ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getInviteCode(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
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
