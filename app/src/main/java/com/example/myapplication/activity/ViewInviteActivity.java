package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ViewInviteActivity extends AppCompatActivity {

    LinearLayout inviteLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invitation);

        inviteLayout = findViewById(R.id.inviteLayout);

        setupLayout();
    }

    private void setupLayout(){

    }


}
