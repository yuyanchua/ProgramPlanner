package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Feedback;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageFeedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {
//
//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    Session session;
    Feedback newFeedback;
    int feedbackId;
    String projectIdStr;
//TODO: Add error for blank feedback?

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        session = Session.getInstance();
        projectIdStr = session.getProjectId();

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project").child(projectIdStr).child("feedback");

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
        newFeedback = new Feedback(session.getUserName(), feedback);
//        if(!feedback.isEmpty())
        ManageFeedback manage = new ManageFeedback(this, projectIdStr);
//        this.feedbackId = manage.getFeedbackId();
        manage.addFeedback(newFeedback);
//        getFeedbackId();
//        addFeedbackToDatabase();
    }

    public void finishSubmit(){
        Toast.makeText(getApplicationContext(), "New Feedback is Added", Toast.LENGTH_SHORT).show();
        finish();
    }

//    private void addFeedbackToDatabase(){
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                String projectId = projectIdStr;
//                System.out.println("Project Id " + projectIdStr);

////                db_ref.child("feedback").setValue(User.username);
////                db_ref.child("feedback").child(User.username);
//
//                db_ref.child(Integer.toString(feedbackId)).setValue(newFeedback);
//                Toast.makeText(getApplicationContext(), "New Feedback is Added", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getFeedbackId(){
//
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap : dataSnapshot.getChildren()){
//                    feedbackId = Integer.parseInt(snap.getKey()) + 1;
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
