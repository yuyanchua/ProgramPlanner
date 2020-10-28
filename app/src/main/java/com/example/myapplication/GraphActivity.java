package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        setupList();
        setupButton();
    }

    private void setupList(){
        ListView graphList = findViewById(R.id.ListViewGallery);
    }

    private void setupButton(){
        Button btUpload = findViewById(R.id.buttonUpload);
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadGraph();
            }
        });

        Button btDelete = findViewById(R.id.buttonDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGraph();
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

    private void uploadGraph(){

    }

    private void deleteGraph(){

    }
}
