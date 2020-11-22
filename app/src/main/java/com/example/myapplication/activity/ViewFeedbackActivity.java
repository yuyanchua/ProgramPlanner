package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Feedback;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageFeedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    List<Feedback> feedbackList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_feedback_view);

//        firebase = FirebaseDatabase.getInstance();
        String projectIdStr = Session.getInstance().getProjectId();
//        db_ref = firebase.getReference("Project").child(projectIdStr).child("feedback");

//        feedbackList = new ArrayList<>();
//        getFeedbackList();
        ManageFeedback manage = new ManageFeedback(this, projectIdStr);
        manage.getFeedbackList();

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void setupFeedbackList(List<Feedback> feedbackList){
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
