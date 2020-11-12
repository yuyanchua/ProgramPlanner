package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ViewApplicationActivity extends AppCompatActivity {
    LinearLayout applicationLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);

        applicationLayout = findViewById(R.id.applicationLayout);
        setupLayout();

    }

    private void setupLayout(){

    }
}
