package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Notebook;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Session;
import com.example.myapplication.element.User;
import com.example.myapplication.engine.ManageNote;
import com.example.myapplication.engine.Validation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotebookActivity extends AppCompatActivity {
//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    Session session = Session.getInstance();
    List<Notebook> bookList;
    Notebook newNotebook;
    Validation validation;
    ManageNote manage;
    int noteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_view);

//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project").child(session.getProjectId()).child("Notebook");

        String username = Session.getInstance().getUserName();
        String projectId = Session.getInstance().getProjectId();

        validation = new Validation(username, projectId);


        bookList = new ArrayList<>();
        manage = new ManageNote(this, session.getProjectId());
        manage.getNoteList();
//        getNoteList();

//        setupNoteList();
        setupButton();

    }

    private boolean validate(){
        boolean isValid = true;
        String message = null;
        if(validation.isExist()){
            String roles = validation.getRoles();
            if(roles.equals("client")){
                message = "Your role has been altered";
                System.out.println(message);
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
        Intent intent = new Intent(NotebookActivity.this, ProjectMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupNoteList(List<Notebook> notebookList){
        this.bookList = notebookList;
        LinearLayout noteLayout = findViewById(R.id.noteList);
        noteLayout.removeAllViews();

        if(notebookList.isEmpty()){
            String info = "There is no note for the project";
            TextView infoView = new TextView(this);
            infoView.setText(info);
            infoView.setTextSize(20);
            infoView.setPadding(5, 5, 5, 5);
            infoView.setClickable(false);
            noteLayout.addView(infoView);
        }
        for(int i = 0; i < bookList.size(); i ++){
            TextView noteView = new TextView(this);
            Notebook temp = bookList.get(i);
            String note = temp.content + "\nBy: " + temp.username;

            noteView.setText(note);
            noteView.setTextSize(20);
            noteView.setPadding(5, 5, 5, 5);

            noteLayout.addView(noteView);
        }

    }

//    private void getNoteList(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap: dataSnapshot.getChildren()){
//                    String username = snap.child("username").getValue().toString();
//                    String content = snap.child("content").getValue().toString();
////                    System.out.println("username: " + username);
////                    System.out.println("Content: " + content);
//                    Notebook tempBook = new Notebook(username, content);
//                    bookList.add(tempBook);
//                }
//                setupNoteList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void setupButton(){
        Button btSubmit = findViewById(R.id.buttonSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                    toSubmit();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                finish();
            }
        });
    }

    private void toSubmit(){
        EditText noteEdit = findViewById(R.id.editTextNote);
        String note = noteEdit.getText().toString();

//        String username = User.username;
        String username = session.getUserName();
        newNotebook = new Notebook(username, note);
//        getNoteId();
//        addNoteToDatabase();
        manage.addNote(newNotebook);

        noteEdit.getText().clear();

    }

    public void finishAdd(){
        Toast.makeText(getApplicationContext(), "New Note is Added", Toast.LENGTH_SHORT).show();
//        recreate();
    }

//    private void addNoteToDatabase(){
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                db_ref.child(Integer.toString(noteId)).setValue(newNotebook);
//                Toast.makeText(getApplicationContext(), "New Note is Added", Toast.LENGTH_SHORT).show();
//                recreate();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    private void getNoteId(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap: dataSnapshot.getChildren()){
//                    noteId = Integer.parseInt(snap.getKey()) + 1;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
