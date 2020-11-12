package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RoleViewActivity extends AppCompatActivity {
    LinearLayout roleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_role);

        roleLayout = findViewById(R.id.roleLayout);

        setupLayout();
        setupButton();
    }

    private void setupLayout(){

    }

    private void setupButton(){
        Button btChangeMag = findViewById(R.id.buttonChangeManager);
        btChangeMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btKick = findViewById(R.id.buttonKick);
        btKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btChangeRole = findViewById(R.id.buttonChangeRole);
        btChangeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


}
