package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatchResult extends AppCompatActivity {
    private TextView allQuestions;
    private TextView allScores;
    private Button buttonPlay;
    private Button menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);

        allQuestions = findViewById(R.id.allQuestions);
        allScores = findViewById(R.id.allScores);

        allQuestions.setText("Questions will go here!");
        allScores.setText("Scores will go here!");

        buttonPlay = findViewById(R.id.button_play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchResult.this, GameOngoing.class));
            }
        });

        menu = findViewById(R.id.button_Menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchResult.this, MainMenu.class));
            }
        });

    }
}
