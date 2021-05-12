package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load language from user settings
        Translations.loadLanguage(getApplicationContext(), getResources());

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