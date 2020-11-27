package com.example.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Roles;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageRoles;

import java.util.ArrayList;
import java.util.List;

public class RoleViewActivity extends AppCompatActivity {
    LinearLayout roleLayout;
    ManageRoles manageRoles;
    Session session;
    List<Integer> roleViewIdList;
    List<Roles> rolesList;
    List<String> kickList;

    Button btKick, btConfirm, btChangeRole, btBack;
    boolean isKick = false, isChange = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_role);

        roleLayout = findViewById(R.id.roleLayout);
        session = Session.getInstance();
        roleViewIdList = new ArrayList<>();
        kickList = new ArrayList<>();

        manageRoles = new ManageRoles(this, session.getProjectId());
        manageRoles.getRolesList();

        setupButton();
    }

    public void setupLayout(final List<Roles> rolesList){
        this.rolesList = rolesList;
        roleLayout = findViewById(R.id.roleLayout);
        roleLayout.removeAllViews();
        for(int i = 0; i < rolesList.size(); i ++){
            final TextView roleView = new TextView(this);
            Roles temp = rolesList.get(i);

//            final String roleInfo = String.format("%s : %s", temp.username, temp.roles);
            int viewId = View.generateViewId();
            roleViewIdList.add(viewId);

            roleView.setId(viewId);
            roleView.setText(temp.toString());
            roleView.setTextSize(20);
            roleView.setPadding(5, 5, 5, 5);
            if(!temp.roles.equalsIgnoreCase("manager")) {
                roleView.setClickable(true);
                roleView.setOnClickListener(v -> {
                    int index = roleLayout.indexOfChild(roleView);
                    if(isKick){
                        kickList.add(rolesList.get(index).username);
                        roleView.setVisibility(View.GONE);

                    }else if (isChange){
                        Roles currRoles = rolesList.get(index);
                        if(currRoles.roles.equalsIgnoreCase("developer")){
                            currRoles.roles = "client";
                        }else{
                            currRoles.roles = "developer";
                        }
                        roleView.setText(currRoles.toString());
                        rolesList.set(index, currRoles);
                    }
                });
            }

            roleLayout.addView(roleView);
        }
    }


    private void setupButton(){
        btConfirm = findViewById(R.id.buttonConfirm);
        btConfirm.setVisibility(View.INVISIBLE);
        btConfirm.setOnClickListener(v -> {
            if(isKick)
                confirmKickDialog(RoleViewActivity.this, kickList);
            else if(isChange)
                manageRoles.changeRole(rolesList);
        });

        btKick = findViewById(R.id.buttonKick);
        btKick.setOnClickListener(v -> toKick());

        btChangeRole = findViewById(R.id.buttonChangeRole);
        btChangeRole.setOnClickListener(v -> toChange());

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> finish());
    }

    private void toChange(){
        String change = "Change Role";
        String confirm = "Cancel";
        if(!isChange){
            isChange = true;
            btChangeRole.setText(confirm);
            btKick.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.VISIBLE);

        }else{
            isChange = false;
//            resetRoleLayout();
            btChangeRole.setText(change);
            btKick.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btConfirm.setVisibility(View.INVISIBLE);

        }
    }

    private void toKick(){
        String kick = "Kick Member";
        String confirm = "Cancel";
        if(!isKick){
            isKick = true;
            btChangeRole.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.VISIBLE);
            btKick.setText(confirm);

        }else{
            isKick = false;
            //reset layout
            resetRoleLayout();
            btChangeRole.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btConfirm.setVisibility(View.INVISIBLE);
            btKick.setText(kick);
        }
    }

    private void resetRoleLayout(){
        int count = roleLayout.getChildCount();
        List<Roles> tempRoleList = new ArrayList<>(rolesList);

        rolesList.clear();
        for(int i = 0; i < count; i ++){
            TextView roleView = (TextView)roleLayout.getChildAt(i);
            int visible = roleView.getVisibility();
            if(visible != View.GONE){
                rolesList.add(tempRoleList.get(i));
            }
        }

    }

    public void reset(){
        Toast.makeText(this, "Member kicked Successfully", Toast.LENGTH_SHORT).show();
        kickList.clear();
        toKick();
//        recreate();
    }

    public void finishChangeRole(){
        Toast.makeText(this, "Roles Changed Successfully", Toast.LENGTH_SHORT).show();
        toChange();
//        recreate();
    }

    private void confirmKickDialog(Context context, List<String> kickMemberList){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        StringBuilder kickInfo = new StringBuilder("Are you sure you want to kick the following members? \n");

        for(String member : kickMemberList){
            kickInfo.append(String.format("\r%s\n", member));
        }
        builder.setMessage(kickInfo.toString())
                .setTitle("Kick Confirmation");
        builder.setPositiveButton("Confirm", (dialog, which) -> manageRoles.kickMember(kickMemberList));

        builder.setNegativeButton("Cancel", (dialog, which) -> cancelKick());
        builder.show();
    }

    private void cancelKick(){
        kickList.clear();
        Toast.makeText(getApplicationContext(), "Cancel Kick Member", Toast.LENGTH_SHORT).show();
        setupLayout(rolesList);
    }




}
