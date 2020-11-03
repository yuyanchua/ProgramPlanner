package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ForgetPass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForgetQuesActivity extends AppCompatActivity {

    private TextView errView;
    private TextView quesView;
    private ForgetPass forget;
//    private FirebaseDatabase firebase;
//    private DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_ques);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
        quesView = findViewById(R.id.securityQuestionTip);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Users");

//        TextView ques_view = findViewById(R.id.securityQuestionTip);
//        Intent intent = getIntent();
        String username = Session.getInstance().getUserName();

        forget = new ForgetPass(this, username);
        forget.getQuestion();
//        setSecurityQuestion();

        FloatingActionButton btConfirm = findViewById(R.id.buttonNextStep);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ansEdit = findViewById(R.id.questionAnswer);
                String quesAns = ansEdit.getText().toString();
//                verifyQuestion(ques_ans);
                forget.verifyQuestion(quesAns);
            }
        });
    }

    public void setErrText(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
    }

    public void setQuesView(String question){
        quesView.setText(question);
    }

    public void finishVerify(){
        startActivity(new Intent(ForgetQuesActivity.this, ResetPassActivity.class));
    }

//    private void setSecurityQuestion(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    String ques_index = dataSnapshot.child(username).child("questionIndex").getValue().toString();
//                    String question = getQuestion(Integer.parseInt(ques_index));
//                    quesView.setText(question);
//                }catch(NullPointerException ex){
//                    errView.setText("Please enter a username");
//                    errView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private void verifyQuestion(final String ques_ans){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String db_ans = dataSnapshot.child(username).child("answer").getValue().toString();
//                if(ques_ans.equals(db_ans)){
//                    startActivity(new Intent(ForgetQuesActivity.this, ResetPassActivity.class));
//                }else{
//                    errView.setText("Incorrect Security Answer");
//                    errView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    private String getQuestion(int ques_index){
//        String question = Question.QUESTION[ques_index];
//        return question;
//    }




}
