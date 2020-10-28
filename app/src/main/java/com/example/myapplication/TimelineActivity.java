package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimelineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);

        Intent intent = getIntent();
        boolean isDeveloper = intent.getExtras().getBoolean("isDeveloper");
        setup(isDeveloper);

    }

    private void setup(boolean isDeveloper){
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
        btAdd.setOnClickListener(new View.OnClickListener() {
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

        if(!isDeveloper){
            btEdit.setVisibility(View.GONE);
            btAdd.setVisibility(View.GONE);
            btDelete.setVisibility(View.GONE);
            btConfirm.setVisibility(View.GONE);
        }
    }

    private void toEdit(){

    }

    private void toAdd(){

    }

    private void toDelete(){

    }

    private void toConfirm(){

    }

}
