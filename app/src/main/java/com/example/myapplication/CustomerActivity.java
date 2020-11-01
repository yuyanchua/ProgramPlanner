package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.element.Project;

public class CustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        TextView titleView = findViewById(R.id.ProjectNameTitle);
        titleView.setText(Project.projectName);

        Button btFeedback = findViewById(R.id.buttonLeaveFeedBack);
        btFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFeedback();
            }
        });

        Button btTimeline = findViewById(R.id.buttonTimeline);
        btTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTimeline();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void toFeedback(){
        startActivity(new Intent(CustomerActivity.this, FeedbackActivity.class));
    }

    private void toTimeline(){
        Intent intent = new Intent(CustomerActivity.this, TimelineActivity.class);
        intent.putExtra("isDeveloper", false);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(CustomerActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
