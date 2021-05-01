package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void showLogin(View v){
        startActivity(new Intent(Welcome.this, Login.class));
    }

    public void showRegister(View v){
        startActivity(new Intent(Welcome.this, Register.class));
    }
}