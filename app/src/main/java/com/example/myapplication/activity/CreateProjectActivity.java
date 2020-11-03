package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ProjectCreate;

public class CreateProjectActivity extends AppCompatActivity {
    TextView errView;
//    FirebaseDatabase firebase;
//    DatabaseReference db_ref, db_ref_roles;
    Session session;
    ProjectCreate projectCreate;

    Project newProject;
    String projectName;
    long projectId;
//    Set<String> allCodes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
//        errView = findViewById(R.id.errorMsg);
//        errView = null;
//        errView.setVisibility(View.INVISIBLE);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project");
//        db_ref_roles = firebase.getReference("Roles");
        session = Session.getInstance();
        projectCreate = new ProjectCreate(this);

//        allCodes = new HashSet<>();
//        getAllCodes();
//        generateProjectId();

        Button btConfirm = findViewById(R.id.buttonConfirm);
//        Button btConfirm = null;
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });

        Button btCancel = findViewById(R.id.buttonCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                backToMain();
            }

        });
    }

    private void createProject(){
        EditText nameEdit = findViewById(R.id.textBoxProjectName);
        projectName = nameEdit.getText().toString();

        projectCreate.createProject(projectName);
    }

//    private void createProject(){
//        EditText projectEdit = findViewById(R.id.textBoxProjectName);
////        EditText projectEdit = null;
//        projectName = projectEdit.getText().toString();
//
//        //Create new project
//        String client_invite = getInviteCode(true);
//        String dev_invite = getInviteCode(false);
//
//
//
//        newProject = new Project(projectId, projectName, client_invite, dev_invite);
//
//        addProjectToDatabase();
//
//    }

    //Checking for largest existing project id
//    private void generateProjectId(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                int maxId = 0;
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
////                Project post = (Project) postSnapshot.getValue();
////                    System.out.println("Get Data: " + postSnapshot.getKey());
//                    projectId = Integer.parseInt(postSnapshot.getKey()) + 1;
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

//    private void getAllCodes(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap : dataSnapshot.getChildren()){
////                    System.out.println(snap.child("clientCode").getValue().toString());
//                    String clientCode = snap.child("clientCode").getValue().toString();
//                    String devCode = snap.child("devCode").getValue().toString();
//                    allCodes.add(clientCode);
//                    allCodes.add(devCode);
////                    System.out.printf("Client Code: %s, Dev Code: %s", clientCode, devCode);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private String getInviteCode(boolean isClient){
//
//        boolean isValid = false;
//        String invite_code = "";
//        do {
//            invite_code = Project.generateCode(isClient);
//            //validate existing code
//            if(allCodes.contains(invite_code)){
//                continue;
//            }else{
//                allCodes.add(invite_code);
//                isValid = true;
//            }
//
//        }while(!isValid);
//        return invite_code;
//    }

    public void finishAddProject(){
        Toast.makeText(getApplicationContext(), "New Project Created Successfully", Toast.LENGTH_SHORT).show();
//        finish();

    }

//    private void addProjectToDatabase(){
//
////        db_ref.addValueEventListener(new ValueEventListener() {
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                db_ref.child(Long.toString(projectId)).setValue(newProject);
//                System.out.println("ProjectId in db_ref: " + projectId);
//                Toast.makeText(getApplicationContext(), "New Project Created Successfully", Toast.LENGTH_SHORT).show();
////                finish();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        System.out.println("ProjectId out: " + projectId);
//
//        db_ref_roles.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println("ProjectId in db_ref_roles: " + projectId);
//                db_ref_roles.child(Long.toString(projectId)).child("ProjectName").setValue(projectName);
//                db_ref_roles.child(Long.toString(projectId )).child(session.getUserName()).child("Roles").setValue("developer");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
////        finish();
//        backToMain();
//    }

    private void backToMain(){
        Intent intent = new Intent(CreateProjectActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
