package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Invitation;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageInvitation;

import java.util.ArrayList;
import java.util.List;

public class ViewInvitationActivity extends AppCompatActivity {

    LinearLayout inviteLayout;
    List<Invitation> inviteList;
    List<Integer> inviteViewIdList;
    Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invitation);

        inviteLayout = findViewById(R.id.inviteLayout);
        inviteViewIdList = new ArrayList<>();
        session = Session.getInstance();

        ManageInvitation manageInvite = new ManageInvitation(this, session.getUserName());
        manageInvite.getInvitationList();

        //Initialise helper class
//        setupLayout();
    }

    public void setupLayout(List<Invitation> inviteList){
        System.out.println(inviteList.toString());
        this.inviteList = inviteList;
        inviteLayout = findViewById(R.id.inviteLayout);

        for(int i = 0; i < inviteList.size(); i ++){
            final TextView inviteView = new TextView(this);
            Invitation temp = inviteList.get(i);

            String inviteInfo = String.format("%s : %s  (%s)", temp.projectId, temp.projectName, temp.projectRole);
            int viewId = View.generateViewId();
            inviteViewIdList.add(viewId);

            inviteView.setId(viewId);
            inviteView.setText(inviteInfo);
            inviteView.setTextSize(20);
            inviteView.setPadding(5, 5, 5, 5);
            inviteView.setClickable(true);
            inviteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = inviteLayout.indexOfChild(inviteView);
                    viewInvite(index);
                }
            });

            inviteLayout.addView(inviteView);
        }

    }

    private void viewInvite(int index){
        //Appear dialog box
    }


}
