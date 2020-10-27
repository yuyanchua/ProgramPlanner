package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

    TextView errView;
    Spinner questionSpin;
    FirebaseDatabase DB;
    DatabaseReference users;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
        DB = FirebaseDatabase.getInstance();
        users = DB.getReference("Users");

        //TODO:set the value for question spinner
        String [] questionArray = {"Question 1", "Question 2", "Question 3"};
        questionSpin = findViewById(R.id.spinnerSecurityQuestion);

        ArrayAdapter questionAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, questionArray);
        questionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        questionSpin.setAdapter(questionAdapter);


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


        questionSpin = findViewById(R.id.spinnerSecurityQuestion);
        int quesIndex = questionSpin.getSelectedItemPosition();

        if(!pass.equals(repass)){
            //Error
            errView.setText("Password incorrect");
            errView.setVisibility(View.VISIBLE);
            return;
        }

        //TODO: Password policy Verification?


        //check whether is possible to signup
        signUpToDatabase(name, pass, quesIndex, answer);
        /*boolean result = signUpToDatabase(name, pass, quesIndex, answer);
        if(!result){
            errView.setText("Username is already in database");
            errView.setVisibility(View.VISIBLE);
            return ;
        }*/
    }

    private void signUpToDatabase(String name, String pass, int quesIndex, String answer){
        byte[] password = pass.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password);
        byte[] digest = md.digest();
        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < digest.length; i++){
            hex.append(Integer.toString((digest[i]&0xff) + 0x100, 16).substring(1));
        }
        String pass_in_string = hex.toString();
        user = new User(name, pass_in_string, quesIndex, answer);
        final String Name = name;
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Name).exists()){
                    errView.setText("Username is already in database");
                    errView.setVisibility(View.VISIBLE);
                }
                else {
                    users.child(Name).setValue(user);
                    Toast.makeText(getApplicationContext(), "Sign up successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
