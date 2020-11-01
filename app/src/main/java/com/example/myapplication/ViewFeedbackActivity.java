package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.element.Feedback;
import com.example.myapplication.element.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {

    FirebaseDatabase firebase;
    DatabaseReference db_ref;
    List<Feedback> feedbackList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_feedback_view);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(Long.toString(Project.projectId)).child("feedback");

        feedbackList = new ArrayList<>();
        getFeedbackList();

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getFeedbackList(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    String feedbackId = snap.getKey();
                    String username = snap.child("username").getValue().toString();
                    String comment = snap.child("comment").getValue().toString();
                    Feedback tempFeed = new Feedback(username, comment);
                    feedbackList.add(tempFeed);
                }
                setupFeedbackList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupFeedbackList(){
        LinearLayout feedLayout = findViewById(R.id.feedbackList);
        for(int i = 0; i < feedbackList.size(); i ++){
            TextView feedView = new TextView(this);
            Feedback temp = feedbackList.get(i);
            String content = temp.comment + "\nBy: " + temp.username;

            feedView.setText(content);
            feedView.setTextSize(25);
            feedView.setPadding(5, 5, 5, 5);

            feedLayout.addView(feedView);
        }
    }
}
