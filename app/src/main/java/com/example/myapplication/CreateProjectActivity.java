package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class CreateProjectActivity extends AppCompatActivity {
    TextView errView;
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    Project newProject;
    long projectId;
    Set<String> allCodes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        errView = findViewById(R.id.errorMsg);
        errView = null;
        errView.setVisibility(View.INVISIBLE);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project");
        allCodes = new HashSet<>();
        getAllCodes();

//        Button btConfirm = findViewById(R.id.Confirm);
        Button btConfirm = null;
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });
    }

    private void createProject(){
//        EditText projectEdit = findViewById(R.id.projectEditView);
        EditText projectEdit = null;
        String projectName = projectEdit.getText().toString();

        if(projectName.isEmpty()){
            errView.setText("Please enter a name for the new project");
            errView.setVisibility(View.VISIBLE);
            return;
        }

        //Create new project
        String client_invite = getInviteCode(true);
        String dev_invite = getInviteCode(false);

        newProject = new Project(projectId, projectName, client_invite, dev_invite);

        //TODO: add the project name to database
        generateProjectId();
        addProjectToDatabase();

    }

    private void generateProjectId(){
        //TODO: Check for largest existing project id
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int maxId = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                Project post = (Project) postSnapshot.getValue();
//                    System.out.println("Get Data: " + postSnapshot.getKey());
                    maxId = Integer.parseInt(postSnapshot.getKey());
                }

                projectId = maxId + 1;
                newProject.projectId = projectId;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAllCodes(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
//                    System.out.println(snap.child("clientCode").getValue().toString());
                    String clientCode = snap.child("clientCode").getValue().toString();
                    String devCode = snap.child("devCode").getValue().toString();
                    allCodes.add(clientCode);
                    allCodes.add(devCode);
                    System.out.printf("Client Code: %s, Dev Code: %s", clientCode, devCode);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getInviteCode(boolean isClient){

        boolean isValid = false;
        String invite_code = "";
        do {
            invite_code = Project.generateCode(isClient);
            //TODO: Validate
            if(allCodes.contains(invite_code)){
                continue;
            }else{
                allCodes.add(invite_code);
                isValid = true;
                break;
            }
        }while(!isValid);
        return invite_code;
    }

    private void addProjectToDatabase(){

        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                db_ref.child(Long.toString(projectId)).setValue(newProject);
                Toast.makeText(getApplicationContext(), "New Project Created Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}