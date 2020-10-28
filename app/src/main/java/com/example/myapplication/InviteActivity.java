package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InviteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code_page);

        setupCode();
        Button btDone = findViewById(R.id.buttonDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupCode(){
        TextView customerView = findViewById(R.id.inviteCodeCustomer);
        customerView.setText(Project.clientCode);

        TextView developerView = findViewById(R.id.inviteCodeDeveloper);
        developerView.setText(Project.devCode);
    }
}
