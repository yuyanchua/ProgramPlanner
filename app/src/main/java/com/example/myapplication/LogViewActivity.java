package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);
    }

    private void setupLogList(){
        ListView logList = findViewById(R.id.listViewLogs);
    }

    private void setupButton(){
        Button btSubmit = findViewById(R.id.buttonSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
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

    private void submit(){
        EditText logEdit = findViewById(R.id.editTextTextMultiLine3);
        String newLog = logEdit.getText().toString();

    }
}
