package com.example.myapplication.engine;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectDelete {

    FirebaseDatabase firebase;
    DatabaseReference db_ref_project, db_ref_roles;

    public ProjectDelete(){
        this.firebase = FirebaseDatabase.getInstance();
        this.db_ref_project = firebase.getReference("Project");
        this.db_ref_roles = firebase.getReference("Roles");
    }


}
