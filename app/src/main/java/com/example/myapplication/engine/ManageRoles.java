package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.RoleViewActivity;
import com.example.myapplication.element.Roles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageRoles {
    FirebaseDatabase firebase;
    DatabaseReference db_ref_roles;
    List<Roles> rolesList;
    RoleViewActivity activity;

    String projectId;

    public ManageRoles(RoleViewActivity activity, String projectId){
        firebase = FirebaseDatabase.getInstance();
        db_ref_roles = firebase.getReference("Roles");
        this.activity = activity;
        this.projectId = projectId;
    }

    public List<Roles> getRolesList(){
        db_ref_roles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rolesList = new ArrayList<>();
                for(DataSnapshot snap : snapshot.child(projectId).getChildren()){
                    String member = snap.getKey();
                    boolean isMember = snap.child("Roles").exists();

                    if(isMember){
                        String role = snap.child("Roles").getValue().toString();
                        Roles temp = new Roles(member, role);
                        rolesList.add(temp);
                    }
                }
                Collections.sort(rolesList, new Roles());
                activity.setupLayout(rolesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rolesList;
    }

    public void kickMember(final List<String> kickList){
        db_ref_roles.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(String username : kickList){
                    System.out.println("Kick " + username);
                    db_ref_roles.child(projectId).child(username).removeValue();
                }

                activity.reset();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void changeRole(final List<Roles> rolesList){
        db_ref_roles.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(Roles temp : rolesList){
                    db_ref_roles.child(projectId).child(temp.username).child("Roles").setValue(temp.roles);
                }
                activity.finishChangeRole();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
