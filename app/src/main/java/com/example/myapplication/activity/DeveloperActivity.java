package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;

public class DeveloperActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_main);

        setup();

        TextView projectNameView = findViewById(R.id.ProjectNameTitle);
        projectNameView.setText(Session.getInstance().getProjectName());


    }

    private void setup(){
        Button btTask = findViewById(R.id.buttonTaskAssignment);
        btTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTaskAssignment();
            }
        });

        Button btInvite = findViewById(R.id.buttonInvite);
        btInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInvite();
            }
        });

        Button btNote = findViewById(R.id.buttonNoteBook);
        btNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNotebook();
            }
        });

        Button btGraph = findViewById(R.id.buttonGraph);
        btGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGraph();
            }
        });

        Button btLog = findViewById(R.id.buttonLog);
        btLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLog();
            }
        });

        Button btTimeline = findViewById(R.id.buttonTimeLine);
        btTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTimeline();
            }
        });

        Button btViewFeedback = findViewById(R.id.buttonViewFeedBack);
        btViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toViewFeedback();
            }
        });

        Button btEvent = findViewById(R.id.buttonEvent);
        btEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEvent();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void toTaskAssignment(){
        startActivity(new Intent(DeveloperActivity.this, TaskAssignActivity.class));
    }

    private void toInvite(){
        startActivity(new Intent(DeveloperActivity.this, InviteActivity.class));
    }

    private void toNotebook(){
        startActivity(new Intent(DeveloperActivity.this, NotebookActivity.class));
    }

    private void toGraph(){
        startActivity(new Intent(DeveloperActivity.this, GraphActivity.class));
    }

    private void toLog(){
        startActivity(new Intent(DeveloperActivity.this, LogViewActivity.class));
    }

    private void toTimeline(){
        Intent intent = new Intent(DeveloperActivity.this, TimelineActivity.class);
        intent.putExtra("isDeveloper", true);
        startActivity(intent);
    }

    private void toViewFeedback(){
        startActivity(new Intent(DeveloperActivity.this, ViewFeedbackActivity.class));
    }

    private void toEvent(){
        Intent intent = new Intent(DeveloperActivity.this, EventActivity.class);
        intent.putExtra("isTimeline", false);
        startActivity(intent);
//        startActivity(new Intent(DeveloperActivity.this, EventActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeveloperActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
