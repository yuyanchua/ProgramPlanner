package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    TextView errView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        Button btAdd = findViewById(R.id.buttonAddEvent);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
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

    private void addEvent(){
        EditText titleEdit = findViewById(R.id.textBoxEventName);
        String title = titleEdit.getText().toString();

        EditText dateEdit = findViewById(R.id.textBoxDate);
        String date = dateEdit.getText().toString();

        CheckBox notify = findViewById(R.id.checkBox);
        boolean isNotify = notify.isSelected();

        //TODO: add to database
    }
}
