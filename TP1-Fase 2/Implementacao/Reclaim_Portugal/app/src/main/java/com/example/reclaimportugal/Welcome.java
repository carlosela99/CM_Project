package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void showRegister(View v) {
        startActivity(new Intent(Welcome.this, Register.class));
    }

    public void showLogin(View v) {
        startActivity(new Intent(Welcome.this, Login.class));
    }
}