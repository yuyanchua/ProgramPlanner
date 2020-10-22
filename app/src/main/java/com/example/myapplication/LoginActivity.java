package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {
    private TextView errView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        errView = findViewById(R.id.errormessage);
        errView.setVisibility(View.INVISIBLE);

        FloatingActionButton btLogin = findViewById(R.id.buttonLogIn);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView forgotText = findViewById(R.id.forgotPassword);
        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
            }
        });

    }

    private void login(){
        EditText userEdit = findViewById(R.id.editTextAccountName);
        String user = userEdit.getText().toString();

        EditText passEdit = findViewById(R.id.editTextPassword);
        String pass = passEdit.getText().toString();

        //TODO: hash pass

        //Verify with the database
        boolean isValid = verifyWithDatabase(user, pass);

        if(!isValid){
            errView.setText("Either Username and Password is Incorrect");
            errView.setVisibility(View.VISIBLE);
        }
    }

    private boolean verifyWithDatabase(String user, String pass){

        return  false;
    }
}
