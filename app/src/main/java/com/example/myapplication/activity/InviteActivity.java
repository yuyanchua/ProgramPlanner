package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageInvite;

public class InviteActivity extends AppCompatActivity {
    TextView customerView, developerView, errView;
    ManageInvite manage;

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code_page);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);
//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project").child(Session.getInstance().getProjectId());

//        setupCode();
        manage = new ManageInvite(this, Session.getInstance().getProjectId());
        manage.getInviteCode();

        setupButton();

    }

    public void setupCode(String clientCode, String devCode){
        customerView = findViewById(R.id.inviteCodeCustomer);
        customerView.setText(clientCode);
//        customerView.setText(Project.clientCode);

        developerView = findViewById(R.id.inviteCodeDeveloper);
        developerView.setText(devCode);
//        developerView.setText(Project.devCode);

//        getCode();
    }

    public void setErrText(String message){
        errView.setText(message);
        errView.setVisibility(View.VISIBLE);
    }

    private void setupButton(){
        Button btInvite = findViewById(R.id.buttonInvite);
        btInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteUser();
            }
        });

        Button btView = findViewById(R.id.buttonApplication);
        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewApplication();
            }
        });

        Button btDone = findViewById(R.id.buttonDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inviteUser(){
        EditText userEdit = findViewById(R.id.textUsername);
        String username = userEdit.getText().toString();

        String projectName = Session.getInstance().getProjectName();

        manage.inviteUser(username, projectName, "unknown");

    }

    private void viewApplication(){

    }

//    private void getCode(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String clientCode = dataSnapshot.child("clientCode").getValue().toString();
//                String devCode = dataSnapshot.child("devCode").getValue().toString();
//
//                customerView.setText(clientCode);
//                developerView.setText(devCode);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
