package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Session;
import com.example.myapplication.engine.ResetPass;


public class ResetPassActivity extends AppCompatActivity {

    private TextView errView;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        errView = findViewById(R.id.errorMessage);
        errView.setVisibility(View.INVISIBLE);
        username = Session.getInstance().getUserName();

        setupButton();
    }

    private void setupButton(){
        Button btConfirm = findViewById(R.id.ConfirmButton);
        btConfirm.setOnClickListener(v -> reset());

        Button btCancel = findViewById(R.id.CancelButton);
        btCancel.setOnClickListener(v -> finishReset());
    }

    private void reset(){
        EditText passEdit = findViewById(R.id.newPassword);
        EditText repeatEdit = findViewById(R.id.reEnterPassword);

        String pass = passEdit.getText().toString();
        String repeat = repeatEdit.getText().toString();

        if(!pass.equals(repeat)){
            setErrView("Password do not match");

        }else{

            //Update password to database
            new ResetPass(this, username).resetPassword(pass);

        }
    }

    public void setErrView(String errMsg){
        errView.setText(errMsg);
        errView.setVisibility(View.VISIBLE);
    }


    public void finishReset(){
        Intent intent = new Intent(ResetPassActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishReset();
    }
}
