package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Log;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageLog;
import com.example.myapplication.engine.Validation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LogViewActivity extends ProgramActivity {

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    Session session;
    List<Log> logList;
//    Calendar calendar;
    ManageLog manageLog;
//    Validation validation;
//    SimpleDateFormat fmtDate;
    LinearLayout logLayout;
    Log newLog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);
        setupUI(findViewById(R.id.logActivity));

        session = Session.getInstance();
        String username = session.getUserName();
        String projectId = session.getProjectId();

        validation = new Validation(username, projectId);
        manageLog = new ManageLog(this, projectId);
//        calendar = Calendar.getInstance();
//        fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        logList = new ArrayList<>();
        manageLog.getLogList();

        setupButton();
    }


    public void setupLogList(List<Log> logList){
        this.logList = logList;
        logLayout = findViewById(R.id.logList);

        if(logList.isEmpty()){
            String info = "There is no log for the project";
            TextView infoView = new TextView(this);
            infoView.setText(info);
            infoView.setTextSize(20);
            infoView.setPadding(5, 5, 5, 5);
            infoView.setClickable(false);
            logLayout.addView(infoView);
        }

        for(int i = 0; i < logList.size(); i ++){
            TextView logView = new TextView(this);
            Log temp = logList.get(i);
            String content = temp.date + " : \n" + temp.content + " \nBy: " + temp.username + "\n";

            logView.setText(content);
            logView.setTextSize(20);
            logView.setPadding(5, 5, 5, 5);

            logLayout.addView(logView);
        }
    }

    private void setupButton(){
        Button btSubmit = findViewById(R.id.buttonSubmit);
        btSubmit.setOnClickListener(v -> {
            if(validateRole()) {
                logList.clear();
                logLayout.removeAllViews();
                submit();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> {
            validateRole();
            finish();
        });
    }

    private void submit(){
        EditText logEdit = findViewById(R.id.editTextTextMultiLine3);
        String logContent = logEdit.getText().toString();

        if(!logContent.isEmpty()) {
//            String currDate = fmtDate.format(calendar.getTime());
            String username = session.getUserName();

//            newLog = new Log(currDate, logContent, username);
            newLog = new Log(logContent, username);
            manageLog.addLog(newLog);
        }else{
            Toast.makeText(getApplicationContext(), "Nothing to add. Please enter a log", Toast.LENGTH_SHORT).show();
        }
//        getLogId();
//
//        addLogToDatabase();
        logEdit.getText().clear();

    }


    public void finishAddLog(){
        Toast.makeText(getApplicationContext(), "New Log is Added", Toast.LENGTH_SHORT).show();
//                recreate();
    }



}
