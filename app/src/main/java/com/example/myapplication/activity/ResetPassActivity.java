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
import com.example.myapplication.engine.ResetPass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ResetPassActivity extends AppCompatActivity {

    private TextView errView;
    private String username;
//    private FirebaseDatabase firebase;
//    private DatabaseReference db_ref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
        username = Session.getInstance().getUserName();
//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Users");

        FloatingActionButton btReset = findViewById(R.id.buttonNextStep);
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset(){
        EditText passEdit = findViewById(R.id.newPassword);
        EditText repeatEdit = findViewById(R.id.reEnterPassword);

        String pass = passEdit.getText().toString();
        String repeat = repeatEdit.getText().toString();

        if(!pass.equals(repeat)){
            String errMsg = "Password Not Match";
            errView.setText(errMsg);
            errView.setVisibility(View.VISIBLE);

        }else{

            //Update password to database
            new ResetPass(this, username).resetPassword(pass);
//            ResetPass reset = new ResetPass(this, username);
//            reset.resetPassword(pass);
        }
    }

//    private void updatePassword(final String password) {
//        byte[] pass_bytes = password.getBytes();
//        MessageDigest md = null;
//        try{
//            md = MessageDigest.getInstance("SHA-256");
//        }catch(NoSuchAlgorithmException exception){
//            exception.printStackTrace();
//        }
//
//        md.update(pass_bytes);
//        byte[] digest = md.digest();
//        StringBuffer hex = new StringBuffer();
//        for(int i = 0; i < digest.length; i ++){
//            hex.append(Integer.toString((digest[i]&0xff) + 0x100, 16).substring(1));
//        }
//        final String pass_str = hex.toString();
//
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                db_ref.child(username).child("password").setValue(pass_str);
//                Intent intent = new Intent(ResetPassActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void finishReset(){
        Intent intent = new Intent(ResetPassActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
