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
import com.example.myapplication.element.Event;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageTimeline;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    Session session;
    List<Event> eventList;
    List<String> deleteList;
    ManageTimeline manage;

    LinearLayout  eventLayout;
    Button btAdd, btDelete, btConfirm, btEdit, btBack;
    boolean isDelete = false, isEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);

        session = Session.getInstance();
        manage = new ManageTimeline(this, session.getProjectId());
//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project").child(session.getProjectId()).child("Event");

        eventList = new ArrayList<>();
        deleteList = new ArrayList<>();

//        getEventList();
        manage.getEventList();

        Intent intent = getIntent();
        boolean isDeveloper = intent.getExtras().getBoolean("isDeveloper");
        setup(isDeveloper);
    }

    private void setup(boolean isDeveloper){
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
//                System.out.println("Select add");
//                startActivity(new Intent(TimelineActivity.this, EventActivity.class));
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
//                confirm();
                manage.confirmRemove(deleteList);
            }
        });

        btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
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
        for(int i = 0; i < eventList.size(); i ++){
            final TextView eventView = new TextView(this);
            Event temp = eventList.get(i);
            final String event = temp.eventDate + ": " + temp.eventTitle;

            eventView.setText(event);
            eventView.setTextSize(25);
            eventView.setPadding(5, 5, 5, 5);
            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index =((ViewGroup) eventView.getParent()).indexOfChild(eventView);
                    if(isDelete){
                        deleteList.add(eventList.get(index).eventId);
                        eventView.setVisibility(View.GONE);
                    }else if (isEdit){
                        editEvent(eventList.get(index).eventId);
                    }
                }
            });
            eventLayout.addView(eventView);
        }
    }

    private void toEdit(){
        if(!isEdit){
            isEdit = true;
            btAdd.setVisibility(View.INVISIBLE);
//            btEdit.setVisibility(View.INVISIBLE);
            btConfirm.setVisibility(View.INVISIBLE);
            btBack.setVisibility(View.INVISIBLE);
            btDelete.setVisibility(View.INVISIBLE);
            btEdit.setText("Cancel Edit");
//            btDelete.setText("Cancel delete");
        }else{
            isEdit = false;
//            resetEventLayout();
            btAdd.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btDelete.setVisibility(View.VISIBLE);
//            btDelete.setText("Delete");
            btEdit.setText("Edit");
        }
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
//            btDelete.setText("Cancel delete");
        }else{
            isDelete = false;
            resetEventLayout();
            btAdd.setVisibility(View.VISIBLE);
            btEdit.setVisibility(View.VISIBLE);
//            btConfirm.setVisibility(View.VISIBLE);
            btBack.setVisibility(View.VISIBLE);
            btDelete.setText("Delete");
        }
    }

//    public void confirm(){
//        manage.confirmRemove(deleteList);
//        deleteList.clear();
//        toDelete();
//    }

    public void reset(){
        deleteList.clear();
        toDelete();
    }

//    private void toConfirm(){
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(String eventId : deleteList){
//                    db_ref.child(eventId).removeValue();
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
//            recreate();
//            eventLayout.removeAllViews();
        }
        recreate();
    }



}
