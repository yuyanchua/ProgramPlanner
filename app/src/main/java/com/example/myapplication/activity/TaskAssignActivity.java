
package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.element.Task;
import com.example.myapplication.engine.ManageTaskView;
import com.example.myapplication.engine.Validation;

import java.util.ArrayList;
import java.util.List;

public class TaskAssignActivity extends AppCompatActivity {

    List<Task> taskList;
    List<String> deleteList;
    ManageTaskView manageTaskView;
    Validation validation;

    LinearLayout taskLayout;
    boolean canDelete = false, isEdit = false;
    Button btAdd, btEdit, btDelete, btConfirm, btBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_view);

        String projectId = Session.getInstance().getProjectId();
        String username = Session.getInstance().getUserName();

        manageTaskView = new ManageTaskView(this, projectId);
        validation = new Validation(username, projectId);

        taskList = new ArrayList<>();
        deleteList = new ArrayList<>();

        manageTaskView.getTaskList();

        setupButton();

    }

    private void setupButton(){
        btEdit = findViewById(R.id.buttonEdit);
        btEdit.setOnClickListener(v -> {
            if(validate())
                toEdit();
        });

        btAdd = findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(v -> {
            if(validate())
                toAdd();
        });

        btDelete = findViewById(R.id.buttonDelete);
        btDelete.setOnClickListener(v -> {
            if(validate())
            toDelete();
        });

        btConfirm = findViewById(R.id.buttonConfirm);
        btConfirm.setVisibility(View.INVISIBLE);
        btConfirm.setOnClickListener(v -> {
            if(validate())
               manageTaskView.confirmRemove(deleteList);
        });

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> {
            validate();
            finish();
        });
    }

    private void hideButton(boolean isHide){
        if(isHide){
            btEdit.setVisibility(View.INVISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
        }else{
            btEdit.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.VISIBLE);
        }
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
        Intent intent = new Intent(TaskAssignActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupTaskView(List<Task> list){
        this.taskList = list;
        setupTaskView();
    }

    private void setupTaskView(){
//        this.taskList = list;
        taskLayout = findViewById(R.id.TaskList);
        taskLayout.removeAllViews();

        if(taskList.isEmpty()){
            String info = "There is no task for the project";
            TextView infoView = new TextView(this);
            infoView.setText(info);
            infoView.setTextSize(20);
            infoView.setPadding(5, 5, 5, 5);
            infoView.setClickable(false);
            taskLayout.addView(infoView);
            hideButton(true);
        }else{
            hideButton(false);
        }

        for(int i = 0; i < taskList.size(); i ++){
            final TextView taskView = new TextView(this);
            Task temp = taskList.get(i);
//            String taskName = temp.task;
            String taskInfo = temp.toString();

            taskView.setText(taskInfo);
            taskView.setTextSize(25);
            taskView.setPadding(5, 5, 5, 5);
            taskView.setClickable(true);
            taskView.setOnClickListener(v -> {
                int index = ((ViewGroup) taskView.getParent()).indexOfChild(taskView);

                if(canDelete){
                    deleteList.add(taskList.get(index).taskId);
                    taskView.setVisibility(View.GONE);
                }else if(isEdit){
                    editTask(taskList.get(index).taskId);
                }
            });
            taskLayout.addView(taskView);
        }
    }

    private void toEdit(){
        String editMsg;
        if(!isEdit){
            isEdit = true;
            btAdd.setVisibility(View.INVISIBLE);
//            btEdit.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.INVISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            editMsg = "Cancel Edit";
        }else{
            isEdit = false;
//            resetTaskLayout();
            btAdd.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.VISIBLE);
            editMsg = "Edit";
        }

        btEdit.setText(editMsg);
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


    public void reset(){
        deleteList.clear();
        toDelete();
    }


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
//        recreate();
    }
}
