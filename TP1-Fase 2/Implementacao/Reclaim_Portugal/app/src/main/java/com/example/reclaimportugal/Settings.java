package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import java.util.Locale;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Settings extends AppCompatActivity {

    private String email;
    private String currentLang;

    private ImageButton btnPT;
    private ImageButton btnEN;

    private static final String PT = "pt";
    private static final String EN = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        email = getIntent().getStringExtra("EMAIL");

        btnPT = findViewById(R.id.PortugalIcon);
        btnEN = findViewById(R.id.EnglishIcon);

        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsChangePassword();
            }
        });

        ImageButton btnBack = (ImageButton) findViewById(R.id.menuSettingsBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        loadLanguage();
    }

    public void openSettingsChangePassword() {
        Intent intent = new Intent(this, SettingsChangePassword.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void changeLanguagePT(View v){
        if (currentLang != PT){
            changeLanguage(PT);
            btnPT.getBackground().setAlpha(255);
            btnEN.getBackground().setAlpha(64);
        }
    }

    public void changeLanguageEN(View v){
        if (currentLang != EN){
            changeLanguage(EN);
            btnPT.getBackground().setAlpha(64);
            btnEN.getBackground().setAlpha(255);
        }
    }

    private void loadLanguage(){
        String lang = Local.getLanguage(getApplicationContext());
        currentLang = lang;

        if (lang == PT){
            btnPT.getBackground().setAlpha(255);
            btnEN.getBackground().setAlpha(64);
        }
        else{
            btnPT.getBackground().setAlpha(64);
            btnEN.getBackground().setAlpha(255);
        }
    }

    private void changeLanguage(String lang){
        currentLang = lang;
        Local.setLanguage(lang, getApplicationContext());
        setAppLocale(lang);
        restartActivity();
    }

    private void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

    private void restartActivity(){
        Intent restart = new Intent(this, this.getClass());
        finish();
        startActivity(restart);
    }
}