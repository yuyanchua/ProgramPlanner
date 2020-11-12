package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageProjectInvite;

public class InviteActivity extends AppCompatActivity {
    TextView customerView, developerView, errView;
    EditText userEdit;
    ManageProjectInvite manage;
    Spinner roleSpin;
    boolean isManager;
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
        manage = new ManageProjectInvite(this, Session.getInstance().getProjectId());
        manage.getInviteCode();

        Intent intent = getIntent();
        isManager = intent.getExtras().getBoolean("isManager");


        setupSpinner();
        setupButton();

    }

    private void setupSpinner(){
        roleSpin = findViewById(R.id.spinnerRole);
        String [] roles = {"Client", "Developer"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, roles);
        roleAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        roleSpin.setAdapter(roleAdapter);
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

        CardView notCard = findViewById(R.id.notification);

        if(!isManager){
            btView.setVisibility(View.GONE);
            notCard.setVisibility(View.GONE);
        }
    }

    private void inviteUser(){
        userEdit = findViewById(R.id.textUsername);
        String username = userEdit.getText().toString();

        String projectName = Session.getInstance().getProjectName();
        String projectRole = roleSpin.getSelectedItem().toString();
        manage.inviteUser(username, projectName, projectRole);

    }


    public void finishInvite(){
        System.out.println("Sent Invitation");
        userEdit.getText().clear();
        Toast.makeText(getApplicationContext(), "Invitation Sent", Toast.LENGTH_SHORT).show();

    }

    private void viewApplication(){
        startActivity(new Intent(InviteActivity.this, ViewApplicationActivity.class));
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
