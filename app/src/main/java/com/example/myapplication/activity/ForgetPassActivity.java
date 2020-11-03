package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ForgetPass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForgetPassActivity extends AppCompatActivity {

    private TextView errView;
//    private FirebaseDatabase firebase;
//    private DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Users");

        FloatingActionButton btNext = findViewById(R.id.buttonNextStep);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUsername();
//                EditText userEdit = findViewById(R.id.accountName);
//                String username = userEdit.getText().toString();
//                new ForgetPass(ForgetPassActivity.this, username);
//                checkDb(username);
            }
        });
    }

    private void verifyUsername(){
        EditText userEdit = findViewById(R.id.accountName);
        String username = userEdit.getText().toString();
        new ForgetPass(this, username);
    }

    public void toForgetQuestion(String username){

        Intent intent = new Intent(ForgetPassActivity.this, ForgetQuesActivity.class);
        Session.getInstance().setUserName(username);
        startActivity(intent);
    }

    public void setErrText(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
    }

//    private void checkDb(final String username){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                 boolean isExist = dataSnapshot.child(username).exists();
//                 if(isExist){
//                     toForgetQuestion(username);
//                 }else {
//                     errView.setText("The Username Entered Does Not Exist");
//                     errView.setVisibility(View.VISIBLE);
//                 }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
}
