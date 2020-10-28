package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JoinProjectActivity extends AppCompatActivity {

    TextView errView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//     setContentView();

//        errView = findViewById();
        errView.setVisibility(View.INVISIBLE);
    }

    private void joinProject(){
        EditText inviteEdit = null;
        EditText projectEdit = null;

        String inviteCode = inviteEdit.getText().toString();
        String projectName = projectEdit.getText().toString();


    }

    private void updateProjectInDatabase(){
        //TODO: check if the project exist
        //If not, return error message
    }
}
