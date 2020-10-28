package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    TextView errView;
    Spinner spinMember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task_assignment);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);
    }

    private void setupSpinner(){
        spinMember = findViewById(R.id.spinnerTeamMember);
    }

    private void setupButton(){
        Button btAddPart = findViewById(R.id.buttonAddParticipants);
        btAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddParticipants();
            }
        });

        Button btAddTask = findViewById(R.id.buttonAddTask);
        btAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddTask();
            }
        });

        Button btRemove = findViewById(R.id.buttonRemove);
        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRemoveParticipants();
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

    private void toAddParticipants(){
        String memberName = spinMember.getSelectedItem().toString();
    }

    private void toAddTask(){
        EditText taskEdit = findViewById(R.id.textBoxTaskName);
        String taskName = taskEdit.getText().toString();


    }

    private void toRemoveParticipants(){

    }


}
