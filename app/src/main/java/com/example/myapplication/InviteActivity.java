package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.element.Project;
import com.example.myapplication.element.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteActivity extends AppCompatActivity {
    TextView customerView, developerView;
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code_page);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(Session.getInstance().getProjectId());

        setupCode();
        Button btDone = findViewById(R.id.buttonDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupCode(){
        customerView = findViewById(R.id.inviteCodeCustomer);
//        customerView.setText(Project.clientCode);

        developerView = findViewById(R.id.inviteCodeDeveloper);
//        developerView.setText(Project.devCode);

        getCode();
    }

    private void getCode(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String clientCode = dataSnapshot.child("clientCode").getValue().toString();
                String devCode = dataSnapshot.child("devCode").getValue().toString();

                customerView.setText(clientCode);
                developerView.setText(devCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
