package com.example.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    ManageInvitation manageInvite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invitation);

        inviteLayout = findViewById(R.id.inviteLayout);
        inviteViewIdList = new ArrayList<>();
        session = Session.getInstance();

        manageInvite = new ManageInvitation(this, session.getUserName());
        manageInvite.getInvitationList();

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
        Invitation invitation = inviteList.get(index);

        showInviteDialog(ViewInvitationActivity.this, invitation, "Project Invitation");

    }

    public void finishViewInvite(String message){
        Toast.makeText(ViewInvitationActivity.this, message, Toast.LENGTH_SHORT).show();
        recreate();
    }


    private void showInviteDialog(Context context, final Invitation invitation, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String inviteDetails = invitation.toString();
        builder.setMessage(inviteDetails)
                .setTitle(title);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                manageInvite.acceptInvite(invitation);
            }
        });

        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                manageInvite.declineInvite(invitation);
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

}
