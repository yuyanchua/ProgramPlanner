package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.element.Task;
import com.example.myapplication.engine.ManageTask;
import com.example.myapplication.engine.Validation;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    TextView errView;
    Spinner spinMember;
    ManageTask manageTask;
    LinearLayout memberLayout;
    List<String> memberList, addMemberList;
    boolean isRemove = false, isEdit = false;
    int taskId;
    String taskIdStr;
    Task newTask;
    Intent lastIntent;
    Validation validation;

    Button btAddPart, btAddTask, btRemove, btBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task_assignment);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        String projectIdStr = Session.getInstance().getProjectId();
        String username = Session.getInstance().getUserName();

        validation = new Validation(username, projectIdStr);

        manageTask = new ManageTask(this, projectIdStr);
        lastIntent = getIntent();

        try{
            String taskId = lastIntent.getStringExtra("taskId");
            if(!taskId.isEmpty()){
//                System.out.println("Task id : " + taskId);
                isEdit = true;
                this.taskId = Integer.parseInt(taskId);
                taskIdStr = taskId;
//                getTaskValue();
                manageTask.setTaskId(this.taskId);
                manageTask.getTaskValue();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        memberLayout = findViewById(R.id.memberLayout);

        memberList = new ArrayList<>();
        addMemberList = new ArrayList<>();

//        getMemberList();
        manageTask.getMemberList();

        setupButton();
    }

    public void setTask(Task task, List<String> memberList){
        EditText titleEdit = findViewById(R.id.textBoxTaskName);
        titleEdit.setText(task.task);

        for(String member : memberList) {
            toAddParticipants(member);
        }
    }

    public void setupSpinner(List<String> list){
        this.memberList = list;
        spinMember = findViewById(R.id.spinnerTeamMember);
//        System.out.println(memberList.toString());
        ArrayAdapter<String> memberAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, memberList);
        memberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinMember.setAdapter(memberAdapter);
    }

    private boolean validate(){
        boolean isValid = true;
        String message = null;
        if(validation.isExist()){
            String roles = validation.getRoles();
            if(roles.equals("client")){
                message = "Your role has been altered";
                isValid = false;
            }
        }else{
            message = "You have been kicked out of the project!";
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
        Intent intent = new Intent(AddTaskActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setupButton(){
        btAddPart = findViewById(R.id.buttonAddParticipants);
        btAddPart.setOnClickListener(v -> {
            if(validate())
                toAddParticipants();
        });

        btAddTask = findViewById(R.id.buttonAddTask);
        String editMsg = "Edit Task";
        if(isEdit)
            btAddTask.setText(editMsg);

        btAddTask.setOnClickListener(v -> {
            if(validate())
                toAddTask();
        });

        btRemove = findViewById(R.id.buttonRemove);
        btRemove.setOnClickListener(v -> {
            if(validate())
                toRemoveParticipants();
        });

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> {
            validate();
            backToLastPage();
        });
    }


    private void toAddParticipants(){
        String memberName = spinMember.getSelectedItem().toString();
        toAddParticipants(memberName);
    }

    private void toAddParticipants(String memberName){
        if(addMemberList.contains(memberName)){
            if(!isEdit) {
                setErrView("Already added the member");
            }
        }else {
            addMemberList.add(memberName);
            final TextView tempView = new TextView(this);
            tempView.setTextSize(30);
            tempView.setText(memberName);
            tempView.setPadding(5, 5, 5, 5);
            tempView.setClickable(true);
            tempView.setOnClickListener(v -> {
                if (isRemove) {
                    memberLayout.removeView(tempView);
                }
            });
            memberLayout.addView(tempView);
        }
    }


    private void toAddTask(){
        EditText taskEdit = findViewById(R.id.textBoxTaskName);
        String taskName = taskEdit.getText().toString();
        if(taskName.isEmpty()){
            setErrView("Please enter a value for task name");
        }else if (addMemberList.size() == 0){
            setErrView("Please add at least one member");
        }else {
            newTask = new Task(taskName, addMemberList);
            if(!isEdit){
                manageTask.addNewTask(newTask);
            }else{
                manageTask.editTask(newTask, taskId);
            }
//                getTaskIdFromDatabase();
//            addTaskToDatabase();
        }
    }


    public void finishAddTask(){
        Toast.makeText(getApplicationContext(), "New Task is Added", Toast.LENGTH_SHORT).show();
        backToLastPage();
    }

    private void backToLastPage(){
        Intent intent = new Intent(AddTaskActivity.this, TaskAssignActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void toRemoveParticipants(){
        //set remove
        String errMsg;
        if(!isRemove) {
            isRemove = true;
            btAddPart.setVisibility(View.INVISIBLE);
            btAddTask.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            errMsg = "Finish Remove";
        }else {
            resetAddMemberList();
            isRemove = false;
            btAddPart.setVisibility(View.VISIBLE);
            btAddTask.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            errMsg = "Remove Members";
        }

        btRemove.setText(errMsg);
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

    private void setErrView(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
    }

}
