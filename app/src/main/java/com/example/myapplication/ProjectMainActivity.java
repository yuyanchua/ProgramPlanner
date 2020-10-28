package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProjectMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_view);
        String welcome = "Welcome, " + User.username;

        TextView welcomeView = findViewById(R.id.WelcomeMessage);
        welcomeView.setText(welcome);

        ListView projectList = findViewById(R.id.ListViewProject);

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

    private void createProject(){
        startActivity(new Intent(ProjectMainActivity.this, CreateProjectActivity.class));
    }

    private void joinProject(){
        startActivity(new Intent(ProjectMainActivity.this, JoinProjectActivity.class));
    }

    private void listProject()  {

    }

    private void logout(){
        Intent intent = new Intent(ProjectMainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
