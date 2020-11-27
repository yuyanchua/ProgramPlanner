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
import com.example.myapplication.element.Event;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageTimeline;
import com.example.myapplication.engine.Validation;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    Session session;
    List<Event> eventList;
    List<String> deleteList;
    ManageTimeline manage;
    Validation validation;

    LinearLayout  eventLayout;
    Button btAdd, btDelete, btConfirm, btEdit, btBack;
    boolean isDelete = false, isEdit = false, isDeveloper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);

        session = Session.getInstance();
        String projectId = session.getProjectId();
        String username = session.getUserName();

        validation = new Validation(username, projectId);

        manage = new ManageTimeline(this, projectId);

        eventList = new ArrayList<>();
        deleteList = new ArrayList<>();

        manage.getEventList();

        Intent intent = getIntent();
        isDeveloper = intent.getExtras().getBoolean("isDeveloper");
        setup(isDeveloper);
    }

    private boolean validate(){
        boolean isValid = true;
        String message = null;
        if(validation.isExist()){
            String roles = validation.getRoles();
            if((roles.equals("client") && isDeveloper) || !roles.equals("client") && !isDeveloper){
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
        Intent intent = new Intent(TimelineActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setup(boolean isDeveloper){
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
                manage.confirmRemove(deleteList);
        });

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> {
            validate();
            finish();
        });

        if(!isDeveloper){
            btEdit.setVisibility(View.GONE);
            btAdd.setVisibility(View.GONE);
            btDelete.setVisibility(View.GONE);
            btConfirm.setVisibility(View.GONE);
        }
    }

//    private void getEventList(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap : dataSnapshot.getChildren()){
//
//                    String eventId = snap.getKey();
//                    String date = snap.child("eventDate").getValue().toString();
//                    String eventTitle = snap.child("eventTitle").getValue().toString();
//                    boolean isNotify = Boolean.getBoolean(snap.child("isNotify").getValue().toString());
//
//                    Event tempEvent = new Event(eventTitle, date, isNotify);
//                    System.out.println(tempEvent.toString());
//                    tempEvent.eventId = eventId;
//                    eventList.add(tempEvent);
//                }
//                setupEventList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void setupEventList(List<Event> list){
        this.eventList = list;
        eventLayout = findViewById(R.id.EventLayout);
        eventLayout.removeAllViews();

        if(list.isEmpty()){
            String info = "There is no event for the project";
            TextView infoView = new TextView(this);
            infoView.setText(info);
            infoView.setTextSize(20);
            infoView.setPadding(5, 5, 5, 5);
            infoView.setClickable(false);
            eventLayout.addView(infoView);
        }

        for(int i = 0; i < eventList.size(); i ++){
            final TextView eventView = new TextView(this);
            Event temp = eventList.get(i);
            final String event = temp.eventDate + ": " + temp.eventTitle;

            eventView.setText(event);
            eventView.setTextSize(25);
            eventView.setPadding(5, 5, 5, 5);
            eventView.setOnClickListener(v -> {
                int index =((ViewGroup) eventView.getParent()).indexOfChild(eventView);
                if(isDelete){
                    deleteList.add(eventList.get(index).eventId);
                    eventView.setVisibility(View.GONE);
                }else if (isEdit){
                    editEvent(eventList.get(index).eventId);
                }
            });
            eventLayout.addView(eventView);
        }
    }

    private void toEdit(){
        String editMsg;
        if(!isEdit){
            isEdit = true;
            btAdd.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
            editMsg = "Cancel Edit";
        }else{
            isEdit = false;
            btAdd.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.VISIBLE);
            editMsg = "Edit";
        }

        btEdit.setText(editMsg);
    }

    private void editEvent(String eventId){
        Intent intent = new Intent(TimelineActivity.this, EventActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    private void toAdd(){
        Intent intent = new Intent(TimelineActivity.this, EventActivity.class);
        intent.putExtra("isTimeline", true);
        startActivity(intent);
//        startActivity(new Intent(TimelineActivity.this, EventActivity.class));

    }

    private void toDelete(){
        if(!isDelete){
            isDelete = true;
            btAdd.setVisibility(View.INVISIBLE);
            btEdit.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
        }else{
            isDelete = false;
            resetEventLayout();
            btAdd.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            String delMsg = "Delete";
            btDelete.setText(delMsg);
        }
    }

    public void reset(){
        deleteList.clear();
        toDelete();
    }

    private void resetEventLayout(){
        int count = eventLayout.getChildCount();

        List<Event> tempEventList = new ArrayList<>(eventList);

        eventList.clear();
        for(int i = 0; i < count; i ++){
            TextView eventView = (TextView)eventLayout.getChildAt(i);
            int visible = eventView.getVisibility();
            if(visible != View.GONE){
                eventList.add(tempEventList.get(i));
            }
        }
    }



}
