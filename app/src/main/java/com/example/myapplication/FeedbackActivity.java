package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    Feedback newFeedback;
    int feedbackId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(Long.toString(Project.projectId)).child("feedback");

        Button btConfirm = findViewById(R.id.button);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        Button btBack = findViewById(R.id.button2);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void submitFeedback(){
        EditText feedEdit = findViewById(R.id.textBoxFeedBack);
        String feedback = feedEdit.getText().toString();
        newFeedback = new Feedback(User.username, feedback);
//        if(!feedback.isEmpty())
        getFeedbackId();
        addFeedbackToDatabase();
    }

    private void addFeedbackToDatabase(){
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String projectId = Long.toString(Project.projectId);
                System.out.println("Project Id " + projectId);
                //TODO: add feedback class, with id, username, comment
//                db_ref.child("feedback").setValue(User.username);
//                db_ref.child("feedback").child(User.username);

                db_ref.child(Integer.toString(feedbackId)).setValue(newFeedback);
                Toast.makeText(getApplicationContext(), "New Feedback is Added", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getFeedbackId(){

        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    feedbackId = Integer.parseInt(snap.getKey()) + 1;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
