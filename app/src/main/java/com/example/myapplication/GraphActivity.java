package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity{

   private RecyclerView RecView;
   private ImageAdapter IAdapter;
   private DatabaseReference DB_Ref;
   private List<Image> Limages;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        RecView = findViewById(R.id.recycler_vi);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecView.setLayoutManager(manager);
        RecView.setHasFixedSize(true);

        Limages = new ArrayList<>();
        DB_Ref = FirebaseDatabase.getInstance().getReference("Project").child(Long.toString(Project.projectId)).child("Images");
        Read_Images();
        setupImage();
        setupView();
        setupButton();
    }

    private void Read_Images(){
        DB_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Image images = snapshot.getValue(Image.class);
                    Limages.add(images);
                }
                IAdapter = new ImageAdapter(GraphActivity.this, Limages);
                RecView.setAdapter(IAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupImage(){

    }

    private void setupView(){

        //ImageView graphList = findViewById(R.id.ImageView);

    }

    private void setupButton(){
        Button btUpload = findViewById(R.id.buttonUpload);
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this, ImageChooser.class));
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
