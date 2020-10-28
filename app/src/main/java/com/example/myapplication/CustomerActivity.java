package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
        Button btFeedback = null;
        btFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFeedback();
            }
        });


    }

    private void toFeedback(){
        startActivity(new Intent(CustomerActivity.this, FeedbackActivity.class));
    }

    private void toTimeline(){
//        startActivity(new Intent(CustomerActivity.this, TimelineActivity.class));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(CustomerActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
