package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.element.Task;
import com.example.myapplication.engine.ManageTaskView;

import java.util.ArrayList;
import java.util.List;

public class TaskAssignActivity extends AppCompatActivity {

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    List<Task> taskList;
    List<String> deleteList;
    ManageTaskView manageTaskView;

    LinearLayout taskLayout;
    boolean canDelete = false, isEdit = false;
    Button btAdd, btEdit, btDelete, btConfirm, btBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_view);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project").child(Session.getInstance().getProjectId()).child("Task");

        manageTaskView = new ManageTaskView(this, Session.getInstance().getProjectId());

        taskList = new ArrayList<>();
        deleteList = new ArrayList<>();

        manageTaskView.getTaskList();

//        getTaskList();
//        setupTaskView();
        setupButton();

    }

    private void setupButton(){
//        Button btEdit = findViewById(R.id.buttonEdit);
        btEdit = findViewById(R.id.buttonEdit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEdit();
            }
        });

        btAdd = findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAdd();
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
        btConfirm.setVisibility(View.INVISIBLE);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toConfirm();
                manageTaskView.confirmRemove(deleteList);
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

//    private void getTaskList(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap : dataSnapshot.getChildren()){
//                    String taskId = snap.getKey();
//                    String taskName = snap.child("task").getValue().toString();
////                    String
//                    Task tempTask = new Task(taskName);
//                    tempTask.taskId = taskId;
//
//                    taskList.add(tempTask);
//                }
//                setupTaskView();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    public void setupTaskView(List<Task> list){
        this.taskList = list;
        setupTaskView();
    }

    private void setupTaskView(){
//        this.taskList = list;
        taskLayout = findViewById(R.id.TaskList);
        for(int i = 0; i < taskList.size(); i ++){
            final TextView taskView = new TextView(this);
            Task temp = taskList.get(i);
//            String taskName = temp.task;
            String taskInfo = temp.toString();

            taskView.setText(taskInfo);
            taskView.setTextSize(25);
            taskView.setPadding(5, 5, 5, 5);
            taskView.setClickable(true);
            taskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = ((ViewGroup) taskView.getParent()).indexOfChild(taskView);

                    if(canDelete){
                        deleteList.add(taskList.get(index).taskId);
                        taskView.setVisibility(View.GONE);
//                        deleteTask(index);
//                        taskLayout.getChildAt(index).setVisibility(View.GONE);
                    }else if(isEdit){
//                        toManage(false);
//                        System.out.println("TaskId: " + taskList.get(index).toString());
                        editTask(taskList.get(index).taskId);
                    }
                }
            });
            taskLayout.addView(taskView);
        }
    }

    private void toEdit(){
        if(!isEdit){
            isEdit = true;
            btAdd.setVisibility(View.INVISIBLE);
//            btEdit.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.INVISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btEdit.setText("Cancel Edit");
        }else{
            isEdit = false;
//            resetTaskLayout();
            btAdd.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.VISIBLE);
            btEdit.setText("Edit");
//            recreate();
        }
    }

    private void editTask(String taskId){
        Intent intent = new Intent(TaskAssignActivity.this, AddTaskActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }

    private void toAdd(){
        Intent intent = new Intent(TaskAssignActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }

//    private void toManage(boolean isAdd){
//        Intent intent = new Intent(TaskAssignActivity.this, AddTaskActivity.class);
//        if(isAdd){
//            intent.putExtra("mode", "add");
//        }else{
//            intent.putExtra("mode", "edit");
//        }
//        startActivity(new Intent(TaskAssignActivity.this, AddTaskActivity.class));
//    }

    private void toDelete(){
        if(!canDelete){
            canDelete = true;
            btAdd.setVisibility(View.INVISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
        }else{
            canDelete = false;
            resetTaskLayout();
            btAdd.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.VISIBLE);
//            recreate();
        }
    }


//    private void deleteTaskFromDatabase(final String taskId, int index){
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                db_ref.child(taskId).removeValue();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        taskList.remove(index);
////        taskIdList.remove(index);
//    }

    public void reset(){
        deleteList.clear();
        toDelete();
    }

//    private void toConfirm(){
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(String taskId : deleteList){
//                    db_ref.child(taskId).removeValue();
//                }
//
//                deleteList.clear();
//                toDelete();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void resetTaskLayout(){
        int count = taskLayout.getChildCount();
        List<Task> tempTaskList = new ArrayList<>(taskList);

        taskList.clear();
        for(int i = 0; i < count; i ++){
            TextView taskView = (TextView)taskLayout.getChildAt(i);
            int visible = taskView.getVisibility();
            if(visible != View.GONE){
                taskList.add(tempTaskList.get(i));
            }
        }
        recreate();
    }
}
