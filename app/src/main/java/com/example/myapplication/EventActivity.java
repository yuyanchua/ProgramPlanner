package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.element.Event;
import com.example.myapplication.element.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    TextView errView, dateView;
    EditText titleEdit;
    DatePickerDialog datePicker;
    Calendar calendar;
    int year, month, day;

    Event newEvent, lastEvent;
    boolean isEdit = false;
    int eventId;
    Intent lastIntent;
    FirebaseDatabase firebase;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        firebase = FirebaseDatabase.getInstance();
        db_ref = firebase.getReference("Project").child(Long.toString(Project.projectId)).child("Event");

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        lastIntent = getIntent();
        try{
            String eventId = lastIntent.getStringExtra("eventId");
            if(!eventId.isEmpty()){
                isEdit = true;
                this.eventId = Integer.parseInt(eventId);
                getEventValue();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        dateView = findViewById(R.id.textViewDate);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(EventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateView.setText((month+ 1) + "-" + dayOfMonth + "-" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        Button btAdd = findViewById(R.id.buttonAddEvent);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getEventValue(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String eventDate = dataSnapshot.child(Integer.toString(eventId)).child("eventDate").getValue().toString();
                String eventTitle = dataSnapshot.child(Integer.toString(eventId)).child("eventTitle").getValue().toString();
                boolean isNotify = Boolean.getBoolean(dataSnapshot.child(Integer.toString(eventId)).child("isNotify").getValue().toString());

                lastEvent = new Event(eventTitle, eventDate, isNotify);
                lastEvent.isNotify = isNotify;

                setEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setEvent(){
        titleEdit = findViewById(R.id.textBoxEventName);
        titleEdit.setText(lastEvent.eventTitle);

        dateView = findViewById(R.id.textViewDate);
        dateView.setText(lastEvent.eventDate);

        CheckBox notify = findViewById(R.id.checkBox);
        notify.setChecked(lastEvent.isNotify);
    }


    private void addEvent(){
        titleEdit = findViewById(R.id.textBoxEventName);
        String title = titleEdit.getText().toString();
        boolean isValid = true;

        if(title.isEmpty()){
            isValid = false;

        }
        String date = dateView.getText().toString();

        if(date.equals("Select to Choose Date")){
            isValid = false;
        }

        CheckBox notify = findViewById(R.id.checkBox);
        boolean isNotify = notify.isChecked();

        if(isValid) {
            newEvent = new Event(title, date, isNotify);
            if(!isEdit)
                getEventId();
            //TODO: add to database
            addEventToDatabase();
        }else{
            errView.setText("Either title or date is invalid");
            errView.setVisibility(View.VISIBLE);
        }
    }

    private void addEventToDatabase(){
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                System.out.println(eventId);

                db_ref.child(Integer.toString(eventId)).setValue(newEvent);
                Toast.makeText(getApplicationContext(), "New Event is Created", Toast.LENGTH_SHORT).show();
                returnPage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getEventId(){
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    eventId = Integer.parseInt(snap.getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void returnPage(){
        boolean isTimeline = lastIntent.getExtras().getBoolean("isTimeline");
        System.out.println("Timeline: " + isTimeline);
        if(isTimeline || isEdit){

            Intent intent = new Intent(EventActivity.this, TimelineActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isDeveloper", true);
            startActivity(intent);
        }else{
            finish();
        }
    }
}
