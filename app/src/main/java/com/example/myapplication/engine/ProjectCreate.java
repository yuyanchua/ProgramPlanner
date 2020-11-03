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
        inviteCodeSet = new HashSet<>();
        getAllCodes();
        generateProjectId();
    }

    public String generateInviteCode(boolean isClient){
        boolean  isValid = false;
        String inviteCode = "" ;
        do{
            inviteCode = Project.generateCode(isClient);
            if(inviteCodeSet.contains(inviteCode)){
                continue;
            }else{
                inviteCodeSet.add(inviteCode);
                isValid = true;
            }
        }while(!isValid);
        return inviteCode;
    }

    public void getAllCodes(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                inviteCodeSet = new HashSet<>();
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

    public void createProject(String projectName, String username){
        this.username = username;
        this.projectName = projectName;

        System.out.println("In Create ");
//        generateProjectId();
        String clientCode = generateInviteCode(true);
        String devCode = generateInviteCode(false);
        System.out.println("Client code: " + clientCode + " Developer Code: " + devCode);
        project = new Project(projectId, projectName, clientCode, devCode);
        System.out.println("New project: " + project.toString());
        addProjectToDatabase();
    }

    public void generateProjectId(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Generating code");
                for(DataSnapshot snap : snapshot.getChildren()){
                    projectId = Integer.parseInt(snap.getKey()) + 1;
                }
                System.out.println("Generated ");
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
                System.out.println(project.toString());
                System.out.println("Add to db");
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
                System.out.println("Projectname in role : " + projectName);
                System.out.println("Username in role: " + username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        activity.backToMain();
    }
}
