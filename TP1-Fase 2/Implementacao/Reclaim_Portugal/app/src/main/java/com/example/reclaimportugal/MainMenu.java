package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    private String email;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        username = getIntent().getStringExtra("USERNAME");
        email = getIntent().getStringExtra("EMAIL");

        TextView tvUsername = findViewById(R.id.username);
        tvUsername.setText(username);

        TextView tvEmail = findViewById(R.id.email);
        tvEmail.setText(email);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra("EMAIL", email);
        intent.putExtra("USERNAME", username);
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void openSubmitQuestion(View view) {
        Intent intent = new Intent(this, SubmitQuestion.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }

    public void openRegions(View v){
        Intent intent = new Intent(this, RegionSelect.class);
        startActivity(intent);
    }
}