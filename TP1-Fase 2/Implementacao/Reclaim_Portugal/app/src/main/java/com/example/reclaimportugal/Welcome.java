package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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

        PlayBackgroundMusic();
    }

    private void PlayBackgroundMusic() {
        Intent intent = new Intent(Welcome.this, BackgroundMusic.class);
        startService(intent);
    }

    public void showRegister(View v) {
        startActivity(new Intent(Welcome.this, Register.class));
    }

    public void showLogin(View v) {
        startActivity(new Intent(Welcome.this, Login.class));
    }
}