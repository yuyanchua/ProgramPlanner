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
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ProjectCreate;

public class CreateProjectActivity extends AppCompatActivity {
    TextView errView;
    Session session;
    ProjectCreate projectCreate;

    String projectName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        session = Session.getInstance();
        projectCreate = new ProjectCreate(this);


        Button btConfirm = findViewById(R.id.buttonConfirm);
        btConfirm.setOnClickListener(v -> createProject());

        Button btCancel = findViewById(R.id.buttonCancel);
        btCancel.setOnClickListener(v -> backToMain());
    }

    private void createProject(){
        EditText nameEdit = findViewById(R.id.textBoxProjectName);
        projectName = nameEdit.getText().toString();
        System.out.println(projectName);
        projectCreate.createProject(projectName, session.getUserName());
    }


    public void finishAddProject(){
        Toast.makeText(getApplicationContext(), "New Project Created Successfully", Toast.LENGTH_SHORT).show();
    }


    public void backToMain(){
        Intent intent = new Intent(CreateProjectActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setErrView(String message){
        errView.setText(message);
        errView.setVisibility(View.VISIBLE);
    }
}
