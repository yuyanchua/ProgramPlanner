package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResetPassActivity extends AppCompatActivity {

    private TextView errView;
    private String username;
    private FirebaseDatabase firebase;
    private DatabaseReference db_ref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
        username = User.username;
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Users");

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
        EditText repassEdit = findViewById(R.id.reEnterPassword);

        String pass = passEdit.getText().toString();
        String repass = repassEdit.getText().toString();

        if(!pass.equals(repass)){
            errView.setText("Password Not Match");
            errView.setVisibility(View.VISIBLE);
            return;
        }

        //Update password to databas
        updatePassword(pass);


    }

    private void updatePassword(final String password) {
        byte[] pass_bytes = password.getBytes();
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        }catch(NoSuchAlgorithmException exception){
            exception.printStackTrace();
        }

        md.update(pass_bytes);
        byte[] digest = md.digest();
        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < digest.length; i ++){
            hex.append(Integer.toString((digest[i]&0xff) + 0x100, 16).substring(1));
        }
        final String pass_str = hex.toString();

        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                db_ref.child(username).child("password").setValue(pass_str);
                Intent intent = new Intent(ResetPassActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
