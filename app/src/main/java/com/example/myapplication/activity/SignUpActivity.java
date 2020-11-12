package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.R;
import com.example.myapplication.element.Question;
import com.example.myapplication.element.User;
import com.example.myapplication.engine.SignUp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUpActivity extends AppCompatActivity {

    TextView errView;
    Spinner questionSpin;
//    FirebaseDatabase DB;
//    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
//        DB = FirebaseDatabase.getInstance();
//        users = DB.getReference("Users");

        questionSpin = findViewById(R.id.spinnerSecurityQuestion);

        ArrayAdapter questionAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Question.QUESTION);
        questionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        questionSpin.setAdapter(questionAdapter);


        FloatingActionButton btSignUp = findViewById(R.id.buttonNextStep);
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

        String name = getEditValue(R.id.enterAccountName);
        String pass = getEditValue(R.id.enterPassword);
        String repass = getEditValue(R.id.reEnterPassword);
        String answer = getEditValue(R.id.enterAnswer);


        questionSpin = findViewById(R.id.spinnerSecurityQuestion);
        int quesIndex = questionSpin.getSelectedItemPosition();

        if(!pass.equals(repass)){
            //Error
//            errView.setText("Password incorrect");
//            errView.setVisibility(View.VISIBLE);
            setErrView("Password incorrect");
            return;
        }

        if(name.isEmpty() || pass.isEmpty() || repass.isEmpty() || answer.isEmpty()){
            setErrView("Fields are Empty");
            return;
        }

//        if(name.length() == 0 || pass.length() == 0 || repass.length() == 0
//                || answer.length() == 0){
//            errView.setText("Fields are empty");
//            errView.setVisibility(View.VISIBLE);
//            return;
//        }
        //TODO: Password policy Verification?

        User user  = new User(name, pass, quesIndex, answer);
        new SignUp(this, user);
//        signupApp.signup();
//        signUpToDatabase(name, pass, quesIndex, answer);
    }

//    private void signUpToDatabase(String name, String pass, int quesIndex, String answer){
//        byte[] password = pass.getBytes();
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        md.update(password);
//        byte[] digest = md.digest();
//        StringBuffer hex = new StringBuffer();
//        for(int i = 0; i < digest.length; i++){
//            hex.append(Integer.toString((digest[i]&0xff) + 0x100, 16).substring(1));
//        }
//        String pass_in_string = hex.toString();
//        user = new User(name, pass_in_string, quesIndex, answer);
//        final String Name = name;
//        users.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(Name).exists()){
//                    errView.setText("Username is already in database");
//                    errView.setVisibility(View.VISIBLE);
//                }
//                else {
//                    users.child(Name).setValue(user);
//                    Toast.makeText(getApplicationContext(), "Sign up successfully!", Toast.LENGTH_SHORT).show();
//                }
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }

    public void setErrView(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
    }

    public void finishSignUp(){
        Toast.makeText(getApplicationContext(), "SignUp successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
