package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ResetPassActivity extends AppCompatActivity {

    TextView errView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);

        FloatingActionButton btReset = findViewById(R.id.buttonNextSteptep);
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset(){
        EditText passEdit = findViewById(R.id.newPassword);
        EditText repassEdit = findViewById(R.id.reEnterPassword);

        String pass = passEdit.getText().toString();
        String repass = repassEdit.getText().toString();

        if(!pass.equals(repass)){
            errView.setText("Password Not Match");
            errView.setVisibility(View.VISIBLE);
            return;
        }

        //Update password to database



    }


}
