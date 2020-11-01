package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.element.Project;
import com.example.myapplication.element.Roles;
import com.example.myapplication.element.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProjectMainActivity extends AppCompatActivity {

    FirebaseDatabase firebase;
    DatabaseReference db_ref;
    List<Roles> projectList;
    List<Integer> projectIdList;
    LinearLayout projectLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_view);
        String welcome = "Welcome, " + User.username;

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Roles");

        TextView welcomeView = findViewById(R.id.WelcomeMessage);
        welcomeView.setText(welcome);

        projectList = new ArrayList<>();
        projectIdList = new ArrayList<>();
        getProjectList();

//        setupProjectList();

        Button btCreate = findViewById(R.id.buttonCreate);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });

        Button btJoin = findViewById(R.id.buttonJoin);
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinProject();
            }
        });

        Button btLogout = findViewById(R.id.buttonLogOut);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void setupProjectList(){
        projectLayout = findViewById(R.id.projectList);
        System.out.println("Project List: " + projectList.toString());
        for(int i = 0; i < projectList.size(); i ++){
            final TextView projectView = new TextView(this);
            Roles temp = projectList.get(i);
            String role = temp.projectId + ": " + temp.projectName;

            int viewId = View.generateViewId();
            projectIdList.add(viewId);

            projectView.setId(viewId);
            projectView.setText(role);
            projectView.setTextSize(20);
            projectView.setPadding(5, 5,5, 5);
            projectView.setClickable(true);
            projectView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = projectLayout.indexOfChild(projectView);
                    toProjectActivity(index);
                }
            });

            projectLayout.addView(projectView);
        }

    }

    private void getProjectList(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    boolean isExist = snap.child(User.username).exists();
//                    System.out.println("IsExist = " + isExist);
//                    System.out.println("Key: " + snap.getKey());
//
//                    System.out.println(snap.getValue().toString());
                    if(isExist) {
                        String projectName = snap.child("ProjectName").getValue().toString();
                        String projectId = snap.getKey();
                        String role = snap.child(User.username).child("Roles").getValue().toString();

                        Roles tempRoles = new Roles(projectId, projectName, role);
                        System.out.println(projectList);
                        projectList.add(tempRoles);
                    }
                }
                setupProjectList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createProject(){
        projectList.clear();
        projectIdList.clear();
        startActivity(new Intent(ProjectMainActivity.this, CreateProjectActivity.class));
    }

    private void joinProject(){
        projectList.clear();
        projectIdList.clear();
        startActivity(new Intent(ProjectMainActivity.this, JoinProjectActivity.class));
    }

    private void toProjectActivity(int projectIndex){
        Roles role = projectList.get(projectIndex);
        Project.projectId = Long.parseLong(role.projectId);
        Project.projectName = role.projectName;

        String userRole = role.roles;
        if(userRole.equals("developer"))
            startActivity(new Intent(ProjectMainActivity.this, DeveloperActivity.class));
        else{
            startActivity(new Intent(ProjectMainActivity.this, CustomerActivity.class));
        }

    }

    private void logout(){
        Intent intent = new Intent(ProjectMainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
