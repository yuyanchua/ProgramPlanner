package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Application;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageApplication;

import java.util.ArrayList;
import java.util.List;

public class ViewApplicationActivity extends AppCompatActivity {
    LinearLayout applicationLayout;
    List<Application> applicationList;
    List<Integer> appViewIdList;
    Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);

        applicationLayout = findViewById(R.id.applicationLayout);
        appViewIdList = new ArrayList<>();
        session = Session.getInstance();

        ManageApplication manageApp = new ManageApplication(this, session.getProjectId());
        manageApp.getApplicationList();

    }

    public void setupLayout(List<Application> applicationList){
        System.out.println(applicationList.toString());
        this.applicationList = applicationList;
        applicationLayout = findViewById(R.id.applicationLayout);

        for(int i = 0; i < applicationList.size(); i ++){
            final TextView appView = new TextView(this);
            Application temp = applicationList.get(i);

            String appInfo = String.format("%s : %s", temp.username, temp.roles);
            int viewId = View.generateViewId();
            appViewIdList.add(viewId);

            appView.setId(viewId);
            appView.setText(appInfo);
            appView.setTextSize(20);
            appView.setPadding(5, 5, 5, 5);
            appView.setClickable(true);
            appView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = applicationLayout.indexOfChild(appView);
                    viewApplication(index);
                }
            });

            applicationLayout.addView(appView);
        }

    }

    private void viewApplication(int index){

    }
}
