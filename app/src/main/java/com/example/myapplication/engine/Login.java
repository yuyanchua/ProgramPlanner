package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.element.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login {
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    LoginActivity activity;
    User user;
    String hashPass;

    public Login(LoginActivity activity, User user){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Users");
        this.activity = activity;
        this.user = user;

        login();
    }

    public void login(){
        hashPass = User.hashPassword(user.password);
        if(hashPass == null){
            activity.setErrText("Encounter unexpected error");
        }else{
            verifyDatabase();
        }
    }

     private void verifyDatabase(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    boolean isExist = snapshot.child(user.username).exists();
                    String dbHashPass = snapshot.child(user.username).child("password").getValue().toString();

                    boolean isMatch = hashPass.equals(dbHashPass);

                    if(!isExist || !isMatch){
                        activity.setErrText("Either Username and Password is Incorrect");
                    }else{
                        activity.finishLogin(user.username);
                    }

                }catch (NullPointerException exception){
//                    exception.printStackTrace();
                    activity.setErrText("Please enter a username");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     }
}
