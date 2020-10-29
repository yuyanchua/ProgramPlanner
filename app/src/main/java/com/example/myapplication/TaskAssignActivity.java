package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskAssignActivity extends AppCompatActivity {

    LinearLayout taskLayout;

    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    private boolean canDelete = false, isSave = false, isStart = true;
    private Map<String, Task> taskIdMap;
    private List<String> taskIdList;
    List<Task> taskList;
    Button btAdd, btDelete, btConfirm, btBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_view);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(Long.toString(Project.projectId)).child("Task");

        taskList = new ArrayList<>();
        taskIdList = new ArrayList<>();
        taskIdMap = new HashMap<>();

        getTaskList();
        setupTaskView();
        setupButton();

    }

    private void getTaskList(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    String taskId = snap.getKey();

                    String taskName = snap.child("task").getValue().toString();
//                    String
                    Task tempTask = new Task(taskName);
                    taskList.add(tempTask);
                    taskIdList.add(taskId);
                    taskIdMap.put(taskId, tempTask);
                    setupTaskView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupTaskView(){
        taskLayout = findViewById(R.id.TaskList);
        for(int i = 0; i < taskList.size(); i ++){
            final TextView taskView = new TextView(this);
            Task temp = taskList.get(i);
            String taskName = temp.task;

            taskView.setText(taskName);
            taskView.setTextSize(20);
            taskView.setPadding(5, 5, 5, 5);
            taskView.setClickable(true);
            taskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = ((ViewGroup) taskView.getParent()).indexOfChild(taskView);

                    if(canDelete){
                        deleteTask(index);
                        taskLayout.getChildAt(index).setVisibility(View.GONE);
                    }else{
                        toManage(false);
                    }
                }
            });
            taskLayout.addView(taskView);
        }
    }

    private void setupButton(){
//        Button btEdit = findViewById(R.id.buttonEdit);
//        btEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toManage(false);
//            }
//        });

        btAdd = findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toManage(true);
            }
        });

        btDelete = findViewById(R.id.buttonDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDelete();
            }
        });

        btConfirm = findViewById(R.id.buttonConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toConfirm();
            }
        });

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toManage(boolean isAdd){
        Intent intent = new Intent(TaskAssignActivity.this, AddTaskActivity.class);
        if(isAdd){
            intent.putExtra("mode", "add");
        }else{
            intent.putExtra("mode", "edit");
        }
        startActivity(new Intent(TaskAssignActivity.this, AddTaskActivity.class));
    }

    private void toDelete(){
        if(!canDelete){
            canDelete = true;
            btAdd.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
        }else{
            canDelete = false;
            btAdd.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            recreate();
        }
    }

    private void deleteTask(int index){
        TextView view = (TextView)taskLayout.getChildAt(index);

        String taskName = view.getText().toString();
        String taskId = taskIdList.get(index);
        deleteTaskFromDatabase(taskId, index);

        taskLayout.removeView(view);
    }

    private void deleteTaskFromDatabase(final String taskId, int index){
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                db_ref.child(taskId).removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        taskList.remove(index);
        taskIdList.remove(index);
    }

    private void toConfirm(){

    }
}
