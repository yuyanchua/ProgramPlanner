package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Roles;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ProjectMain;

import java.util.ArrayList;
import java.util.List;

public class ProjectMainActivity extends AppCompatActivity {

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    List<Roles> projectList;
    List<Integer> projectIdList;
    LinearLayout projectLayout;
    Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_view);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Roles");
        session = Session.getInstance();

        TextView welcomeView = findViewById(R.id.WelcomeMessage);
        String welcome = session.getUserName();
        welcomeView.setText(welcome);

//        projectList = new ArrayList<>();
        projectIdList = new ArrayList<>();
//        getProjectList();
        ProjectMain main = new ProjectMain(this, session.getUserName());
        main.getProjectList();
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

    public void setupProjectList(List<Roles> projectList){
        this.projectList = projectList;
        projectLayout = findViewById(R.id.projectList);
        System.out.println("Project List: " + projectList.toString());
        for(int i = 0; i < projectList.size(); i ++){
            final TextView projectView = new TextView(this);
            Roles temp = projectList.get(i);
//            String role = temp.projectId + ": " + temp.projectName;
            String role = String.format("%s:%s  (%s)", temp.projectId, temp.projectName, temp.roles);
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

//    private void getProjectList(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap: dataSnapshot.getChildren()){
//
//                    boolean isExist = snap.child(session.getUserName()).exists();
////                    System.out.println("IsExist = " + isExist);
////                    System.out.println("Key: " + snap.getKey());
////
////                    System.out.println(snap.getValue().toString());
//                    if(isExist) {
//                        String projectName = snap.child("ProjectName").getValue().toString();
//                        String projectId = snap.getKey();
//                        String role = snap.child(session.getUserName()).child("Roles").getValue().toString();
//
//                        Roles tempRoles = new Roles(projectId, projectName, role);
//                        System.out.println(projectList);
//                        projectList.add(tempRoles);
//                    }
//                }
//                setupProjectList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }



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

        long projectId = Long.parseLong(role.projectId);
        String projectName = role.projectName;

        Project project = new Project(projectId, projectName);
        session.setCurrProject(project);

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
