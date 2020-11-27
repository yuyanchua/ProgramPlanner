package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView fullScreen = findViewById(R.id.FullScreenImageView);

        Intent intent = getIntent();
        if(intent != null){
            Uri imageUri = intent.getData();
            if(imageUri != null && fullScreen != null){
                Picasso.with(this)
                        .load(imageUri)
                        .into(fullScreen);
            }
        }
        fullScreen.setOnClickListener(v -> finish());
    }
}
