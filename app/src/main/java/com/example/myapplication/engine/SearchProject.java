package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.element.Project;
import com.example.myapplication.element.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchProject {

    FirebaseDatabase firebase;
    DatabaseReference db_ref;
//    List<Project> projectList;
    Map<Long, Project> projectMap;

    public SearchProject(){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project");
        retrieveData();
    }

    private void retrieveData(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                projectList = new ArrayList<>();
                projectMap = new HashMap<>();

                for(DataSnapshot snap: snapshot.getChildren()){
                    String projectIdStr = snap.getKey();
                    long projectId = Long.parseLong(projectIdStr);
                    String projectName = snap.child("projectName").getValue().toString();

                    Project tempProject = new Project(projectId, projectName);
//                    projectList.add(tempProject);
                    projectMap.put(projectId, tempProject);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Project searchProjectById(long projectId){
        if(projectMap.containsKey(projectId)){
            return projectMap.get(projectId);
        }else{
            return null;
        }
    }

    public List<Project> searchProjectByName(String name){
        List<Project> projectList = new ArrayList<>();

        for (long projectId : projectMap.keySet()){
            Project temp = projectMap.get(projectId);
            String projectName = temp.projectName;
            if(projectName.contains(name)){
                projectList.add(temp);
            }

        }
        return projectList;
    }

}
