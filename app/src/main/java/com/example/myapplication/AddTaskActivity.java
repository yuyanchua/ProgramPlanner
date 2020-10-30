package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    TextView errView;
    Spinner spinMember;
    LinearLayout memberLayout;
    List<String> memberList, addMemberList;
    boolean isRemove = false, isEdit = false;
    int taskId;
    String taskIdStr;
    Task newTask, lastTask;
    Intent lastIntent;

    Button btAddPart, btAddTask, btRemove, btBack;
    FirebaseDatabase firebase;
    DatabaseReference db_ref, db_ref_roles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task_assignment);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(Long.toString(Project.projectId)).child("Task");
        db_ref_roles = firebase.getReference("Roles").child(Long.toString(Project.projectId));

        lastIntent = getIntent();
        try{
            String taskId = lastIntent.getStringExtra("taskId");
            if(!taskId.isEmpty()){
                isEdit = true;
                this.taskId = Integer.parseInt(taskId);
                taskIdStr = taskId;
                getTaskValue();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        memberLayout = findViewById(R.id.memberLayout);

        memberList = new ArrayList<>();
        addMemberList = new ArrayList<>();

        getMemberList();

        setupButton();
    }

    private void getTaskValue(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String title = dataSnapshot.child(taskIdStr).child("task").getValue().toString();
                List<String> memberList = new ArrayList<>();

                DataSnapshot memberSnap = dataSnapshot.child(taskIdStr).child("memberList");
                for(DataSnapshot snap : memberSnap.getChildren()){
                    String key = snap.getKey();
                    System.out.println(snap.getValue().toString());
                    String member = snap.getValue().toString();
                    memberList.add(member);
                }

                lastTask = new Task(title, memberList);
//                setTask();
                EditText titleEdit = findViewById(R.id.textBoxTaskName);
                titleEdit.setText(lastTask.task);

                for(String member : memberList) {
                    toAddParticipants(member);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setTask(){
//        EditText titleEdit = findViewById(R.id.textBoxTaskName);
//        titleEdit.setText(lastTask.task);
//
//        for(String member : memberList) {
//            toAddParticipants(member);
//        }
    }

    private void setupSpinner(){
        spinMember = findViewById(R.id.spinnerTeamMember);
        System.out.println(memberList.toString());
        ArrayAdapter memberAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, memberList);
        memberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinMember.setAdapter(memberAdapter);
    }

    private void getMemberList(){
        db_ref_roles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    String member = snap.getKey();
                    boolean isMember = snap.child("Roles").exists();
                    System.out.println(member + ": " + isMember);
                    if(isMember){
                        String role = snap.child("Roles").getValue().toString();
                        if(role.equals("developer")){
                            System.out.println(member + " is developer");
                            memberList.add(member);
                        }
                    }

                }
                setupSpinner();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupButton(){
        btAddPart = findViewById(R.id.buttonAddParticipants);
        btAddPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddParticipants();
            }
        });

        btAddTask = findViewById(R.id.buttonAddTask);
        if(isEdit)
            btAddTask.setText("Edit Task");
        btAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddTask();
            }
        });

        btRemove = findViewById(R.id.buttonRemove);
        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRemoveParticipants();
            }
        });

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                backToLastPage();
            }
        });
    }


    private void toAddParticipants(){
        String memberName = spinMember.getSelectedItem().toString();
        toAddParticipants(memberName);
    }

    private void toAddParticipants(String memberName){
        if(addMemberList.contains(memberName)){
            if(!isEdit) {
                errView.setText("Already added the member");
                errView.setVisibility(View.VISIBLE);
            }
        }else {
            addMemberList.add(memberName);
            final TextView tempView = new TextView(this);
            tempView.setTextSize(30);
            tempView.setText(memberName);
            tempView.setPadding(5, 5, 5, 5);
            tempView.setClickable(true);
            tempView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isRemove) {
//                    memberLayout.indexOfChild(tempView);
                        memberLayout.removeView(tempView);
                    }
                }
            });
            memberLayout.addView(tempView);
        }
    }


    private void toAddTask(){
        EditText taskEdit = findViewById(R.id.textBoxTaskName);
        String taskName = taskEdit.getText().toString();
        if(taskName.isEmpty()){
            errView.setText("Please enter a value for task name");
            errView.setVisibility(View.VISIBLE);
        }else {
            newTask = new Task(taskName, addMemberList);
            if(!isEdit)
                getTaskIdFromDatabase();
            addTaskToDatabase();
        }
    }

    private void getTaskIdFromDatabase(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    taskId = Integer.parseInt(snap.getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addTaskToDatabase(){
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                db_ref.child(Integer.toString(taskId)).setValue(newTask);
                Toast.makeText(getApplicationContext(), "New Task is Added", Toast.LENGTH_SHORT).show();
                backToLastPage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void backToLastPage(){
        Intent intent = new Intent(AddTaskActivity.this, TaskAssignActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void toRemoveParticipants(){
        //set remove

        if(isRemove == false) {
            isRemove = true;
            btAddPart.setVisibility(View.INVISIBLE);
            btAddTask.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btRemove.setText("Finish Remove");
        }else {
            resetAddMemberList();
            isRemove = false;
            btAddPart.setVisibility(View.VISIBLE);
            btAddTask.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btRemove.setText("Remove Members");
        }
    }

    private void resetAddMemberList(){
        int count = memberLayout.getChildCount();
        addMemberList.clear();

        for(int i = 0; i < count; i ++){
            TextView memberView = (TextView)memberLayout.getChildAt(i);
            String tempMemberName = memberView.getText().toString();
            addMemberList.add(tempMemberName);
        }
    }


}
