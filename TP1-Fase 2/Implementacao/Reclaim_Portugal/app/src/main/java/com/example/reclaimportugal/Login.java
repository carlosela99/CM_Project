package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void showWelcome(View v){
        startActivity(new Intent(Login.this, Welcome.class));
    }

    public void showForgotPassword(View v){
        startActivity(new Intent(Login.this, Welcome.class));
    }
}