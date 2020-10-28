package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TaskAssignActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_view);

        setupTaskView();
        setupButton();

    }

    private void setupTaskView(){
        ListView taskList = findViewById(R.id.listViewTasks);
    }

    private void setupButton(){
        Button btEdit = findViewById(R.id.buttonEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEdit();
            }
        });

        Button btAdd = findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAdd();
            }
        });

        Button btDelete = findViewById(R.id.buttonDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDelete();
            }
        });

        Button btConfirm = findViewById(R.id.buttonConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toConfirm();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toEdit(){

    }

    private void toAdd(){
        startActivity(new Intent(TaskAssignActivity.this, AddTaskActivity.class));
    }

    private void toDelete(){

    }

    private void toConfirm(){

    }
}
