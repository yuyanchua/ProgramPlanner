package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.JoinProjectActivity;
import com.example.myapplication.element.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinProject {
    FirebaseDatabase firebase;
    DatabaseReference db_ref_project, db_ref_roles;

    JoinProjectActivity activity;
    boolean isExist, isValid, isDeveloper;
    String projectName, inviteCode, username, projectId;

    public JoinProject(JoinProjectActivity activity){
        firebase = FirebaseDatabase.getInstance();
        db_ref_project = firebase.getReference("Project");
        db_ref_roles = firebase.getReference("Roles");
        this.activity = activity;
    }

    public void joinProject(String projectName, String inviteCode, String username){
        this.projectName = projectName;
        this.inviteCode = inviteCode;
        this.username = username;

        joinProjectInDatabase();
    }

    public void setProjectValue(Project project){
        this.projectName = project.projectName;
        this.projectId = Long.toString(project.projectId);
    }

    public void joinProjectInDatabase(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isValid = false;
                for(DataSnapshot snap : snapshot.getChildren()){
                    try{
                        String name = snap.child("projectName").getValue().toString();
                        if(!projectName.equals(name)){
                            continue;
                        }
                        long projectId = Long.parseLong(snap.getKey());

                        String clientCode = snap.child("clientCode").getValue().toString();
                        String devCode = snap.child("devCode").getValue().toString();
                        Project project = new Project(projectId, name, clientCode, devCode);
                        if(inviteCode.equals(clientCode)) {
                            activity.setProjectValue(project);
                            setProjectValue(project);
                            isValid = true;
                            isDeveloper = false;
                        }else if (inviteCode.equals(devCode)){
                            activity.setProjectValue(project);
                            setProjectValue(project);
                            isValid = true;
                            isDeveloper = true;
                        }
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }
                if(!isValid){
                    activity.setErrText("Either Project Does Not Exist or Invitation Code is Not Match");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db_ref_roles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(isValid){
                    boolean isExist = snapshot.child(projectId).child(username).exists();
                    if(!isExist){
                        String userRole = "client";
                        if(isDeveloper){
                            userRole = "developer";
                        }

                        db_ref_roles.child(projectId).child("ProjectName").setValue(projectName);
                        db_ref_roles.child(projectId).child(username).child("Roles").setValue(userRole);

                        activity.finishJoin(isDeveloper);
                    }
                }
                if (isExist) {
                    activity.setErrText("The User already join the project");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
