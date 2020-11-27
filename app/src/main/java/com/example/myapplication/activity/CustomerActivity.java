package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.Validation;

public class CustomerActivity extends AppCompatActivity {

    Session session = Session.getInstance();
    Validation validation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        TextView titleView = findViewById(R.id.ProjectNameTitle);
//        titleView.setText(Project.projectName);
        titleView.setText(session.getCurrProject().projectName);

        String username = session.getUserName();

        validation = new Validation(username, session.getProjectId());

        Button btFeedback = findViewById(R.id.buttonLeaveFeedBack);
        btFeedback.setOnClickListener(v -> {
            if(validate())
                toFeedback();
        });

        Button btTimeline = findViewById(R.id.buttonTimeline);
        btTimeline.setOnClickListener(v -> {
            if(validate())
                  toTimeline();
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> onBackPressed());

    }
    private boolean validate(){
        boolean isValid = true;
        String message = null;

        if(validation.isExist()){
            String roles = validation.getRoles();
            if(!roles.equalsIgnoreCase("client")){
                message = "Your role has been altered";
                isValid = false;
            }
        }else{
            message = "You have been kicked out from the project!";
            isValid = false;
        }

        if(!isValid){
            backToProjectPage(message);
        }

        return isValid;
    }

    private void backToProjectPage(String message){
        if(message == null){
            message = "Encountered unexpected error";
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        onBackPressed();
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
