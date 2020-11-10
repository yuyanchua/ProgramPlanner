package com.example.myapplication.engine;

import androidx.annotation.NonNull;

import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.element.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login {
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    LoginActivity activity;
//    User user;
//    String hashPass;
    List<User> userList;


    public Login(LoginActivity activity){
        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Users");
        this.activity = activity;
//        this.user = user;

//        login();
        retrieveDatabase();
    }

    public void login(User user){

        String passHash = User.hashPassword(user.password);
        if(passHash == null){
            activity.setErrText("Encounter unexpected error");
        }else{
            user.password = passHash;
            verifyUser(user);
        }
    }

    private void retrieveDatabase(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = new ArrayList<>();

                for(DataSnapshot snap: snapshot.getChildren()){
                    String username = snap.getKey();
                    String passHash = snap.child("password").getValue().toString();
                    User temp = new User(username, passHash);
                    System.out.println(temp);
                    userList.add(temp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verifyUser(User loginUser){
        for(User temp: userList){
            if(temp.isEqual(loginUser)) {
                activity.finishLogin(loginUser.username);
                return;
            }
        }
        activity.setErrText("Either Username or Password is Incorrect");
    }


//     private void verifyDatabase(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try{
//                    boolean isExist = snapshot.child(user.username).exists();
//                    String dbHashPass = snapshot.child(user.username).child("password").getValue().toString();
//
//                    boolean isMatch = hashPass.equals(dbHashPass);
//
//                    if(!isExist || !isMatch){
//                        activity.setErrText("Either Username and Password is Incorrect");
//                    }else{
//                        activity.finishLogin(user.username);
//                    }
//
//                }catch (NullPointerException exception){
////                    exception.printStackTrace();
//                    activity.setErrText("Please enter a username");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//     }
}
