package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.CreateProjectActivity;
import com.example.myapplication.element.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class ProjectCreate {
    FirebaseDatabase firebase;
    DatabaseReference db_ref_project, db_ref_roles;

    CreateProjectActivity activity;
    Project project;
    long projectId;
    String projectName;
    String username;
    Set<String> inviteCodeSet;

    public ProjectCreate(CreateProjectActivity activity){
        firebase = FirebaseDatabase.getInstance();
        db_ref_project = firebase.getReference("Project");
        db_ref_roles = firebase.getReference("Roles");

        this.activity = activity;
        getAllCodes();
    }

    public String generateInviteCode(boolean isClient){
        boolean  isValid = false;
        String inviteCode = ""  ;
        do{
            inviteCode = Project.generateCode(isClient);
            if(inviteCodeSet.contains(inviteCode)){
                continue;
            }else{
                inviteCodeSet.add(inviteCode);
                isValid = true;
            }
        }while(isValid);
        return inviteCode;
    }

    public void getAllCodes(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inviteCodeSet = new HashSet<>();

                for(DataSnapshot snap : snapshot.getChildren()){
                    String clientCode = snap.child("clientCode").getValue().toString();
                    String devCode = snap.child("devCode").getValue().toString();

                    inviteCodeSet.add(clientCode);
                    inviteCodeSet.add(devCode);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createProject(String projectName){
        generateProjectId();
        String clientCode = generateInviteCode(true);
        String devCode = generateInviteCode(false);

        project = new Project(projectId, projectName, clientCode, devCode);
        addProjectToDatabase();
    }

    public void generateProjectId(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    projectId = Integer.parseInt(snap.getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addProjectToDatabase(){
        db_ref_project.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                db_ref_project.child(Long.toString(projectId)).setValue(project);
                activity.finishAddProject();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db_ref_roles.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                db_ref_roles.child(Long.toString(projectId)).child("ProjectName").setValue(projectName);
                db_ref_roles.child(Long.toString(projectId)).child(username).child("Roles").setValue("developer");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
