package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinProjectActivity extends AppCompatActivity {

    TextView errView;
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_project);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project");

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
                finish();
            }
        });
    }

    private void joinProject(){
        EditText inviteEdit = findViewById(R.id.textBoxInvitationCode);
        EditText projectEdit = findViewById(R.id.textBoxProjectName);

        String inviteCode = inviteEdit.getText().toString();
        String projectName = projectEdit.getText().toString();

        if(inviteCode.isEmpty() || projectName.isEmpty()){
            errView.setText("Either Project Name or the Invite Code is Empty");
            errView.setVisibility(View.VISIBLE);
        }



//        int inviteInt = Integer.parseInt(inviteCode);
//        if(inviteInt % 2 == 1){
//            toClient();
//        }else{
//            toDeveloper();
//        }
        joinProjectInDatabase(projectName, inviteCode);

    }

    private void joinProjectInDatabase(final String projectName, final String inviteCode){
        //TODO: check if the project exist
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isValid = false;
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    String name = snap.child("projectName").getValue().toString();
                    if(!projectName.equals(name)){
                        continue;
                    }
                    long projectId = Long.parseLong(snap.getKey().toString());

                    String clientCode = snap.child("clientCode").getValue().toString();
                    String devCode = snap.child("devCode").getValue().toString();
                    if(inviteCode.equals(clientCode)){
                        setProjectValue(projectId, name, clientCode, devCode);
                        isValid = true;
                        toClient();
                    }
                    if(inviteCode.equals(devCode)){
                        setProjectValue(projectId, name, clientCode, devCode);
                        isValid = true;
                        toDeveloper();
                    }

                }
                if(!isValid) {
                    errView.setText("Either Project Does Not Exist or Invite Code Incorrect");
                    errView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //If not, return error message
    }

    private void setProjectValue(long projectId, String projectName, String clientCode, String devCode){
        Project.projectId = projectId;
        Project.projectName = projectName;
        Project.clientCode = clientCode;
        Project.devCode = devCode;
    }

    private void toDeveloper(){

        startActivity(new Intent(JoinProjectActivity.this, DeveloperActivity.class));
    }

    private void toClient(){
        startActivity(new Intent(JoinProjectActivity.this, CustomerActivity.class));
    }
}
