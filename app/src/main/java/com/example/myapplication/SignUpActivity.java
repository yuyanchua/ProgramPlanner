package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUpActivity extends AppCompatActivity {

    TextView errView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);

        FloatingActionButton btSignUp = findViewById(R.id.buttonNextSteptep);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private String getEditValue(int id){
        EditText tempEdit = findViewById(id);
        return tempEdit.getText().toString();
    }

    private void signup(){
//        EditText nameEdit = findViewById(R.id.enterAccountName);
//
//        EditText passEdit = findViewById(R.id.enterPassword);
//        EditText repassEdit = findViewById(R.id.reEnterPassword);

        String name = getEditValue(R.id.enterAccountName);
        String pass = getEditValue(R.id.enterPassword);
        String repass = getEditValue(R.id.reEnterPassword);
        String answer = getEditValue(R.id.enterAnswer);

        Spinner quesSpin = findViewById(R.id.spinnerSecurityQuestion);
        int quesIndex = quesSpin.getSelectedItemPosition();

        if(!pass.equals(repass)){
            //Error
            errView.setText("Password incorrect");
            errView.setVisibility(View.VISIBLE);
            return;
        }

        //TODO: Password policy Verification?


        //check whether is possible to signup
        boolean result = signUpToDatabase(name, pass, quesIndex, answer);
        if(!result){
            errView.setText("Username is already in database");
            errView.setVisibility(View.VISIBLE);
            return ;
        }
    }

    private boolean signUpToDatabase(String name, String pass, int quesIndex, String answer){

        //password in Hash?

        return false;
    }
}
