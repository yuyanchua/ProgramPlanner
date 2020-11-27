package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Feedback;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageFeedback;

import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_feedback_view);

        String projectIdStr = Session.getInstance().getProjectId();
        ManageFeedback manage = new ManageFeedback(this, projectIdStr);
        manage.getFeedbackList();

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> finish());

    }


    public void setupFeedbackList(List<Feedback> feedbackList){
        LinearLayout feedLayout = findViewById(R.id.feedbackList);

        if(feedbackList.isEmpty()){
            String info = "There is no feedback for the project";
            TextView infoView = new TextView(this);
            infoView.setText(info);
            infoView.setTextSize(20);
            infoView.setPadding(5, 5, 5, 5);
            infoView.setClickable(false);
            feedLayout.addView(infoView);
        }

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
