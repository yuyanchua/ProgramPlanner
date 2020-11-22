package com.example.myapplication.activity;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Event;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ManageEvent;
import com.example.myapplication.engine.Validation;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    TextView errView, dateView;
    EditText titleEdit;
    DatePickerDialog datePicker;
    Calendar calendar;
    int year, month, day;

    ManageEvent manage;
    Event newEvent;
    Validation validation;
    boolean isEdit = false;
    int eventId;
    Intent lastIntent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        String projectIdStr = Session.getInstance().getProjectId();
        String username = Session.getInstance().getUserName();

        validation = new Validation(username, projectIdStr);

        errView = findViewById(R.id.errorMessageTip);
        errView.setVisibility(View.INVISIBLE);

        manage = new ManageEvent(this, projectIdStr);


        lastIntent = getIntent();
        try{
            String eventId = lastIntent.getStringExtra("eventId");
            if(!eventId.isEmpty()){
                isEdit = true;
                this.eventId = Integer.parseInt(eventId);
                manage.setEventId(this.eventId);
                manage.getEventValue();
//                getEventValue();
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
                        (view, year, month, dayOfMonth) -> {
                            String date = (month+ 1) + "-" + dayOfMonth + "-" + year;
                            dateView.setText(date);
                        }, year, month, day);
                datePicker.show();
            }
        });

        Button btAdd = findViewById(R.id.buttonAddEvent);
        btAdd.setOnClickListener(v -> {
            if(validate())
                addEvent();
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(v -> {
            validate();
            finish();
        });
    }


    public void setEvent(Event event){
        this.eventId = Integer.parseInt(event.eventId);

        titleEdit = findViewById(R.id.textBoxEventName);
        titleEdit.setText(event.eventTitle);

        dateView = findViewById(R.id.textViewDate);
        dateView.setText(event.eventDate);

        CheckBox notify = findViewById(R.id.checkBox);
        System.out.println("Check : " + event.isNotify);
        notify.setChecked(event.isNotify);
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
        System.out.println("IsNotify: " + isNotify);

        if(isValid) {
            newEvent = new Event(title, date, isNotify);
//            if(!isEdit)
//                getEventId();
//            addEventToDatabase();
            if(!isEdit){
                manage.addNewEvent(newEvent);
            }else{
                manage.editEvent(eventId, newEvent);
            }
        }else{
            errView.setText("Either title or date is invalid");
            errView.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(EventActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

//    private void addEventToDatabase(){
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                System.out.println(eventId);
//
//                db_ref.child(Integer.toString(eventId)).setValue(newEvent);
//                Toast.makeText(getApplicationContext(), "New Event is Created", Toast.LENGTH_SHORT).show();
//                returnPage();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void finishAdd(){
        Toast.makeText(getApplicationContext(), "New Event is Created", Toast.LENGTH_SHORT).show();
        returnPage();
    }

//    private void getEventId(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot snap : dataSnapshot.getChildren()){
//                    eventId = Integer.parseInt(snap.getKey()) + 1;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

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
