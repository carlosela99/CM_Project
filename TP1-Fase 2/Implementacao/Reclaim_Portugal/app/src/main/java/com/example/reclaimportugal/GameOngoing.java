package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class GameOngoing extends AppCompatActivity {
    private TextView countdownText;
    private ImageButton pauseButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilli = 60000;
    private boolean timerRunning;
    private PopupWindow popupWindow;
    private TextView question;
    private TextView answerLeft;
    private TextView answerRight;
    int random;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ongoing);

        random = new Random().nextInt(20)+1;
        countdownText = findViewById(R.id.countdown);

        question = findViewById(R.id.gameQuestion);
        question.setText("Questions will be inserted here.");

        answerLeft = findViewById(R.id.answerLeft);
        answerLeft.setText("Answer.");

        answerRight = findViewById(R.id.answerRight);
        answerRight.setText("Answer.");

        pauseButton = findViewById(R.id.pauseGame);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v);
            }
        });
        startTimer();
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilli = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(GameOngoing.this, MatchResult.class));
            }
        }.start();
        timerRunning = true;
    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMilli / 60000;
        int seconds = (int) timeLeftInMilli % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);
    }

    public void onButtonShowPopupWindowClick(View view) {


        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_game_popup, null);


        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT + 1000;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT + 700;

        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void continueGame(View v){
        popupWindow.dismiss();
    }

    public void quitGame(View v){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}