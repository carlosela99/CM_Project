package com.example.reclaimportugal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MatchResult extends AppCompatActivity {
    private TextView allQuestions;
    private TextView rightScore;
    private TextView wrongScore;
    private TextView allScore;
    private Button buttonPlay;
    private Button menu;
    private int score;
    private int wrong;
    private HashMap<String, Boolean> highlightQuestions;
    private int id;
    private int nQuestions;


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
        highlightQuestions = new HashMap<>();

        score = getIntent().getIntExtra("score", -1);
        wrong =  getIntent().getIntExtra("wrong", -1);
        nQuestions = getIntent().getIntExtra("numberQuestions", 20);
        id =  getIntent().getIntExtra("regionID", -1);
        highlightQuestions.putAll((HashMap<String, Boolean>)getIntent().getSerializableExtra("highlightedQuestions"));


        allQuestions = findViewById(R.id.allQuestions);
        rightScore = findViewById(R.id.correctScores);
        wrongScore = findViewById(R.id.wrongScores);
        allScore = findViewById(R.id.allScores);

        allQuestions.setText("");
        Iterator it = highlightQuestions.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            allQuestions.append(Html.fromHtml( "" + pair.getKey(), Html.FROM_HTML_MODE_LEGACY));
            it.remove();
        }
        allQuestions.setMovementMethod(new ScrollingMovementMethod());

        Local.incrementGamesCount(getApplicationContext());
        Local.incrementCorrectAnswersCount(score, getApplicationContext());
        Local.incrementWrongAnswersCount(wrong, getApplicationContext());

        rightScore.setText("Correct\n"  + score);
        wrongScore.setText("Wrong\n" + wrong);
        allScore.setText("Answered\n" + (score + wrong));

        Local.setRegionScore(((score * 100) / 20), ""+id, getApplicationContext());

        buttonPlay = findViewById(R.id.button_play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchResult.this, GameOngoing.class);
                intent.putExtra("regionIDGame", id);
                finish();
                startActivity(intent);
            }
        });

        menu = findViewById(R.id.button_Menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
