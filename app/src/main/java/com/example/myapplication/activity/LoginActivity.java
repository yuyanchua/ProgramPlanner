package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.element.User;
import com.example.myapplication.engine.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private TextView errView;
    private EditText userEdit;
    private EditText passEdit;

//    FirebaseDatabase DB;
//    DatabaseReference users;
    Session session =  Session.getInstance();
    Login login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        errView = findViewById(R.id.errormessage);
        errView.setVisibility(View.INVISIBLE);

        userEdit = findViewById(R.id.editTextAccountName);
        passEdit = findViewById(R.id.editTextPassword);

        login = new Login(this);
        setup();
//        DB = FirebaseDatabase.getInstance();
//        users = DB.getReference("Users");

//        FloatingActionButton btLogin = findViewById(R.id.buttonLogIn);
//        btLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });
//
//        TextView forgotText = findViewById(R.id.forgotPassword);
//        forgotText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
//            }
//        });

    }

    private void setup(){
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
        String username = userEdit.getText().toString();
        String pass = passEdit.getText().toString();

        User user = new User(username, pass);
//        new Login(this, user);

        login.login(user);
    }

    public void setErrText(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
        passEdit.getText().clear();

    }

    public void finishLogin(String username){
        session.setUserName(username);
        userEdit.getText().clear();
        passEdit.getText().clear();
        startActivity(new Intent(LoginActivity.this, ProjectMainActivity.class));
    }

//    private void login(){
//        userEdit = findViewById(R.id.editTextAccountName);
//        String user = userEdit.getText().toString();
//
//        passEdit = findViewById(R.id.editTextPassword);
//        String pass = passEdit.getText().toString();
//
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
//
//        //Verify with the database
//        verifyWithDatabase(user, pass_in_string);
//
//    }

//    private void verifyWithDatabase(final String user, final String pass){
//        final EditText passEdit = findViewById(R.id.editTextPassword);
//        users.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    boolean isExist = dataSnapshot.child(user).exists();
//                    boolean isEqual = dataSnapshot.child(user).child("password").getValue().toString().equals(pass);
//
//                    if (!isExist || !isEqual) {
//                        errView.setText("Either Username and Password is Incorrect");
//                        errView.setVisibility(View.VISIBLE);
//                        passEdit.getText().clear();
//                    }else{
//                        session.setUserName(user);
//                        startActivity(new Intent(LoginActivity.this, ProjectMainActivity.class));
//                        userEdit.getText().clear();
//                        passEdit.getText().clear();
//                    }
//                }catch(NullPointerException ex){
//                    errView.setText("Please enter a username");
//                    errView.setVisibility(View.VISIBLE);
//                    passEdit.getText().clear();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//    }


}
