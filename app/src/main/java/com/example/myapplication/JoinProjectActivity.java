package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinProjectActivity extends AppCompatActivity {

    TextView errView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_project);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        Button btJoin = findViewById(R.id.buttonJoin);
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinProject();
            }
        });

        Button btCancel = findViewById(R.id.buttonCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void joinProject(){
        EditText inviteEdit = findViewById(R.id.textBoxInvitationCode);
        EditText projectEdit = findViewById(R.id.textBoxProjectName);

        String inviteCode = inviteEdit.getText().toString();
        String projectName = projectEdit.getText().toString();

        int inviteInt = Integer.parseInt(inviteCode);
        if(inviteInt % 2 == 1){
            toClient();
        }else{
            toDeveloper();
        }

    }

    private void updateProjectInDatabase(){
        //TODO: check if the project exist
        //If not, return error message
    }

    private void toDeveloper(){

        startActivity(new Intent(JoinProjectActivity.this, DeveloperActivity.class));
    }

    private void toClient(){
        startActivity(new Intent(JoinProjectActivity.this, CustomerActivity.class));
    }
}
