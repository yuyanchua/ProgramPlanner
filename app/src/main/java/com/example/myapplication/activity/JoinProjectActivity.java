package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    Button btJoin, btApply, btView, btCancel;
    Spinner roleSpin;

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

        setupSpinner();
        setupButton();
    }

    private void setupSpinner(){
        roleSpin = findViewById(R.id.spinnerRole);
        String [] roles = {"Client", "Developer"};
        ArrayAdapter roleAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, roles);

        roleAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roleSpin.setAdapter(roleAdapter);

    }

    private void setupButton(){
        btJoin = findViewById(R.id.buttonJoin);
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinProject();
            }
        });

        btApply = findViewById(R.id.buttonApply);
        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyProject();
            }
        });

        btView = findViewById(R.id.buttonInvite);
        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
//        EditText projectEdit = findViewById(R.id.textBoxProjectName);

        String inviteCode = inviteEdit.getText().toString();
//        String projectName = projectEdit.getText().toString();

        if(inviteCode.isEmpty()){
            String errMsg = "Please enter an invite Code";
            setErrText(errMsg);
//            errView.setText("Either Project Name or the Invite Code is Empty");
//            errView.setVisibility(View.VISIBLE);
        }else{
            joinProject.joinProject(inviteCode, session.getUserName());
        }
    }

    private void applyProject(){
        EditText projectEdit = findViewById(R.id.textBoxProjectName);
        String projectName = projectEdit.getText().toString();

        String projectRoles = roleSpin.getSelectedItem().toString();

        if(projectName.isEmpty()){
            String errMsg = "Please enter an project name";
            setErrText(errMsg);
        }else{
            joinProject.applyProject(projectName, projectRoles, session.getUserName());
        }

    }

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

    private void toDeveloper(){
        startActivity(new Intent(JoinProjectActivity.this, DeveloperActivity.class));
    }

    private void toClient(){
        startActivity(new Intent(JoinProjectActivity.this, CustomerActivity.class));
    }
}
