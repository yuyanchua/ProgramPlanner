package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {

    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project");

        Button btConfirm = null;
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

    }

    private void submitFeedback(){
        EditText feedEdit = null;
        String feedback = feedEdit.getText().toString();
        addFeedbackToDatabase(feedback);
    }

    private void addFeedbackToDatabase(final String feedback){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String projectId = Long.toString(Project.projectId);
                //TODO: add feedback class, with id, username, comment
//                dataSnapshot.child(projectId).child("feedback");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
