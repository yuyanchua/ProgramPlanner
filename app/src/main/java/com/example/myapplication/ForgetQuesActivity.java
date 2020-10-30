package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetQuesActivity extends AppCompatActivity {

    private TextView errView;
    private TextView quesView;
    private String username;
    private FirebaseDatabase firebase;
    private DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_ques);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
        quesView = findViewById(R.id.securityQuestionTip);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Users");

        //TODO: get question index from database
        TextView ques_view = findViewById(R.id.securityQuestionTip);
        Intent intent = getIntent();
        username = User.username;

        setSecurityQuestion();

        FloatingActionButton btConfirm = findViewById(R.id.buttonNextStep);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ansEdit = findViewById(R.id.questionAnswer);
                String ques_ans = ansEdit.getText().toString();
                verifyQuestion(ques_ans);
            }
        });
    }

    private void setSecurityQuestion(){
        //TODO: get question index from database
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String ques_index = dataSnapshot.child(username).child("questionIndex").getValue().toString();
                    String question = getQuestion(Integer.parseInt(ques_index));
                    quesView.setText(question);
                }catch(NullPointerException ex){
                    errView.setText("Please enter a username");
                    errView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void verifyQuestion(final String ques_ans){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String db_ans = dataSnapshot.child(username).child("answer").getValue().toString();
                if(ques_ans.equals(db_ans)){
                    startActivity(new Intent(ForgetQuesActivity.this, ResetPassActivity.class));
                }else{
                    errView.setText("Incorrect Security Answer");
                    errView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //TODO: Implement Question
    // if we don't use Email, how do we implement this. Or could use the cell phone to receive the verify message
    private String getQuestion(int ques_index){
        return "Question " + ques_index;
    }


}
