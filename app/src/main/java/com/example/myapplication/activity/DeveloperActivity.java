package com.example.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageDeveloper;

public class DeveloperActivity extends AppCompatActivity {

    boolean isConfirm;
    boolean isManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_main);

        Intent intent = getIntent();
        isManager = intent.getExtras().getBoolean("isManager");

        TextView titleView = findViewById(R.id.mainTitle);
        if(isManager) {
            String title = "Manager Main Page";
            titleView.setText(title);
        }
        setup(isManager);

        TextView projectNameView = findViewById(R.id.ProjectNameTitle);
        projectNameView.setText(Session.getInstance().getProjectName());


    }

    private void setup(boolean isManager){
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

        Button btManageRole = findViewById(R.id.buttonManageRole);
        btManageRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toManageRole();
            }
        });

        Button btDelete = findViewById(R.id.buttonDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteConfirmation(DeveloperActivity.this, "message", "title");
                deleteProject();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(!isManager){
            btManageRole.setVisibility(View.GONE);
            btDelete.setVisibility(View.GONE);

        }

    }

    private void toTaskAssignment(){
        startActivity(new Intent(DeveloperActivity.this, TaskAssignActivity.class));
    }

    private void toInvite(){
        Intent intent =  new Intent(DeveloperActivity.this, InviteActivity.class);
        intent.putExtra("isManager", isManager);
        startActivity(intent);
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

    private void toManageRole(){
        startActivity(new Intent(DeveloperActivity.this, RoleViewActivity.class));
    }

    private void deleteProject(){
        String message = "Are you sure you want to delete the project?";
        String title = "Delete Project";

        boolean isConfirm = deleteConfirmation(DeveloperActivity.this, message, title);
        if(isConfirm){
            ManageDeveloper manage = new ManageDeveloper(this);
            manage.removeData(Session.getInstance().getProjectId());
        }
    }

    private boolean deleteConfirmation(Context context, String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        System.out.println("Build alert dialog");
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isConfirm = true;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isConfirm = false;
            }
        });

        builder.show();
        return isConfirm;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeveloperActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void finishDelete(){
        Toast.makeText(getApplicationContext(), "Project delete Sucessfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
