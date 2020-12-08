package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Feedback;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageFeedback;
import com.example.myapplication.engine.Validation;

public class FeedbackActivity extends ProgramActivity {

    Session session;
    Feedback newFeedback;
//    Validation validation;

    String projectIdStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        setupUI(findViewById(R.id.feedbackViewActivity));

        session = Session.getInstance();
        projectIdStr = session.getProjectId();
        String username = session.getUserName();
//        validation = new Validation(username, projectIdStr);
        setValidation(username, projectIdStr);

        Button btConfirm = findViewById(R.id.button);
        btConfirm.setOnClickListener(v -> {
            if(validateRole())
                submitFeedback();
        });

        Button btBack = findViewById(R.id.button2);
        btBack.setOnClickListener(v -> {
            validateRole();
            finish();
        });
    }

//    private boolean validate(){
//        boolean isValid = true;
//        String message = null;
//        if(validation.isExist()){
//            String roles = validation.getRoles();
//            if(!roles.equals("client")){
//                message = "Your role has been altered";
//                isValid = false;
//            }
//        }else{
//            message = "You have been kicked out of the project!";
//            isValid = false;
//        }
//
//        if(!isValid){
//            backToProjectPage(message);
//        }
//
//        return isValid;
//    }
//
//    private void backToProjectPage(String message){
//        if(message == null){
//            message = "Encountered unexpected error";
//        }
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(FeedbackActivity.this, ProjectMainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

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

}
