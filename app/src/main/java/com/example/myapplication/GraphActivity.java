package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.element.Image;
import com.example.myapplication.element.Project;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListen {
    private RecyclerView RecView;
    private ImageAdapter IAdapter;
    private DatabaseReference DB_Ref;
    private FirebaseStorage Storage;
    private List<Image> Limages;
    private ProgressBar pro_Cir;
    private ArrayList<String> Del_list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        RecView = findViewById(R.id.recycler_vi);
        RecView.setLayoutManager(new LinearLayoutManager(this));
        RecView.setHasFixedSize(true);

        pro_Cir = findViewById(R.id.progress_circular);
        Limages = new ArrayList<>();
        Del_list = new ArrayList<>();

        IAdapter = new ImageAdapter(GraphActivity.this, Limages);
        RecView.setAdapter(IAdapter);
        IAdapter.setOnItemClickListener(GraphActivity.this);

        DB_Ref = FirebaseDatabase.getInstance().getReference("Project").child(Long.toString(Project.projectId)).child("Images");
        Storage = FirebaseStorage.getInstance();

        Read_Images();
        setupButton();
    }

    private void Read_Images(){
        DB_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Del_list.clear();
                Limages.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Image images = snapshot.getValue(Image.class);
                    Del_list.add(snapshot.getKey());
                    Limages.add(images);
                }


                IAdapter.notifyDataSetChanged();
                pro_Cir.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                pro_Cir.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setupButton(){
        Button btUpload = findViewById(R.id.buttonUpload);
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this, ImageChooser.class));
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

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(), "Normal click!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDeleteClick(int position) {
        Image Item = Limages.get(position);
        final String Key = Del_list.get(position);
        final int pot = position;
        StorageReference S_Ref = Storage.getReferenceFromUrl(Item.ImageUrl);
        S_Ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DB_Ref.child(Key).removeValue();
                Del_list.remove(pot);
                Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
