package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.JoinProject;

public class JoinProjectActivity extends AppCompatActivity {

    TextView errView;
//    FirebaseDatabase firebase;
//    DatabaseReference db_ref, db_ref_roles;
    Session session;
    JoinProject joinProject;
//    boolean isExist, isValid, isDeveloper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_project);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project");
//        db_ref_roles = firebase.getReference("Roles");
        session = Session.getInstance();
        joinProject = new JoinProject(this);

        Button btJoin = findViewById(R.id.buttonJoin);
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinProject();
            }
        });

        Button btCancel = findViewById(R.id.buttonCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                Intent intent = new Intent(JoinProjectActivity.this, ProjectMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void joinProject(){
        EditText inviteEdit = findViewById(R.id.textBoxInvitationCode);
        EditText projectEdit = findViewById(R.id.textBoxProjectName);

        String inviteCode = inviteEdit.getText().toString();
        String projectName = projectEdit.getText().toString();

        if(inviteCode.isEmpty() || projectName.isEmpty()){
            String errMsg = "Either Project Name or the Invite Code is Empty";
            setErrText(errMsg);
//            errView.setText("Either Project Name or the Invite Code is Empty");
//            errView.setVisibility(View.VISIBLE);
        }else{
            joinProject.joinProject(projectName, inviteCode, session.getUserName());
        }

//        int inviteInt = Integer.parseInt(inviteCode);
//        if(inviteInt % 2 == 1){
//            toClient();
//        }else{
//            toDeveloper();
//        }
//        joinProjectInDatabase(projectName, inviteCode);

    }

//    private void joinProjectInDatabase(final String projectName, final String inviteCode){
//        //TODO: check if the project exist
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                isValid = false;
//                for(DataSnapshot snap: dataSnapshot.getChildren()){
//                    try {
//                        String name = snap.child("projectName").getValue().toString();
//                        if (!projectName.equals(name)) {
//                            continue;
//                        }
//                        long projectId = Long.parseLong(snap.getKey().toString());
////                        Project.projectId = projectId;
//
//                        //TODO: check whether is redundant
//                        session.getCurrProject().projectId = projectId;
//
//                        String clientCode = snap.child("clientCode").getValue().toString();
//                        String devCode = snap.child("devCode").getValue().toString();
//                        if (inviteCode.equals(clientCode)) {
//                            setProjectValue(projectId, name, clientCode, devCode);
//                            isValid = true;
//                            isDeveloper = false;
////                            toClient();
//                        }
//                        if (inviteCode.equals(devCode)) {
//                            setProjectValue(projectId, name, clientCode, devCode);
//                            isValid = true;
//                            isDeveloper = true;
////                            toDeveloper();
//                        }
//                    }catch(Exception exception){
//                        exception.printStackTrace();
//                    }
//                }
//                if(!isValid) {
//                    errView.setText("Either Project Does Not Exist or Invite Code Incorrect");
//                    errView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        db_ref_roles.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String username = session.getUserName();
//                if(isValid){
////                    Project project = session.getCurrProject();
//                    String projectIdStr = session.getProjectId();
//                    boolean isExist = dataSnapshot.child(projectIdStr).child(username).exists();
//                    if(!isExist){
//                        if(isDeveloper){
//                            db_ref_roles.child(projectIdStr).child("ProjectName").setValue(projectName);
//                            db_ref_roles.child(projectIdStr).child(username).child("Roles").setValue("developer");
//                            toDeveloper();
//                        }else{
//                            db_ref_roles.child(projectIdStr).child("ProjectName").setValue(projectName);
//                            db_ref_roles.child(projectIdStr).child(username).child("Roles").setValue("client");
//                            toClient();
//                        }
//                    }
//                }
//                if(isExist){
//                    errView.setText("The User already join the project");
//                    errView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        //If not, return error message
//    }

    public void setProjectValue(Project project){
        session.setCurrProject(project);
    }

    public void finishJoin(boolean isDeveloper){
        if(isDeveloper)
            toDeveloper();
        else
            toClient();
    }

    public void setErrText(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
    }

//    private void setProjectValue(long projectId, String projectName, String clientCode, String devCode){
////        Project.projectId = projectId;
////        Project.projectName = projectName;
////        Project.clientCode = clientCode;
////        Project.devCode = devCode;
//        Project temp = new Project(projectId, projectName, clientCode, devCode);
//        session.setCurrProject(temp);
//    }

    private void toDeveloper(){
        startActivity(new Intent(JoinProjectActivity.this, DeveloperActivity.class));
    }

    private void toClient(){
        startActivity(new Intent(JoinProjectActivity.this, CustomerActivity.class));
    }
}
