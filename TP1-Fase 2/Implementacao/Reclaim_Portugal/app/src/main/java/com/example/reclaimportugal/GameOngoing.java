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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
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
    private int random;
    private int counter;
    private String language;
    private int id;
    private String json;
    private JSONObject obj;
    private JSONArray jArray;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private boolean rightOrWrong;
    private HashMap<String, Boolean> highlightQuestions;
    private int start;
    private int nQuestions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ongoing);

        id = getIntent().getIntExtra("regionIDGame", -1);
        try {
            jArray = new JSONArray(getJson());

            if(id == 0){
                start = 0;
                nQuestions = 20;
            }else if(id == 1){
                start = 20;
                nQuestions = 16;
            }else if(id == 2){
                start = 36;
                nQuestions = 15;
            }else if(id == 3){
                start = 51;
                nQuestions = 16;
            }else if(id == 4){
                start = 67;
                nQuestions = 15;
            }else if(id == 5){
                start = 82;
                nQuestions = 20;
            }else if(id == 6){
                start = 102;
                nQuestions = 13;
            }else if(id == 7){
                start = 116;
                nQuestions = 15;
            }else if(id == 8){
                start = 131;
                nQuestions = 16;
            }else if(id == 9){
                start = 147;
                nQuestions = 13;
            }else{
                start = 0;
                nQuestions = 30;
            }
            counter = nQuestions;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        language = Locale.getDefault().getLanguage();
        countdownText = findViewById(R.id.countdown);
        question = findViewById(R.id.gameQuestion);
        answerLeft = findViewById(R.id.answerLeft);
        answerRight = findViewById(R.id.answerRight);

        highlightQuestions = new HashMap<>();

        newQuestion();

        pauseButton = findViewById(R.id.pauseGame);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v);
            }
        });
        startTimer();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilli = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //startActivity(new Intent(GameOngoing.this, MatchResult.class));
                Intent intent = new Intent(GameOngoing.this, MatchResult.class);
                intent.putExtra("score", correctAnswers);
                intent.putExtra("wrong", wrongAnswers);
                //intent.putExtra("regionID", id);
                startActivity(intent);
            }
        }.start();
        timerRunning = true;
    }

    public void updateTimer() {
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

    public void continueGame(View v) {
        popupWindow.dismiss();
    }

    public void quitGame(View v) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public String getJson() {
        try {
            //InputStream is = getAssets().open("regionquestions" + id + ".json");
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void chooseLeft(View v) throws JSONException {
        counter--;
        if(rightOrWrong == false){
            correctAnswers++;
            placeQuestion(true);
        }else {
            wrongAnswers++;
            placeQuestion(false);
        }
        newQuestion();
    }

    public void chooseRight(View v) throws JSONException {
        counter--;
        if(rightOrWrong) {
            correctAnswers++;
            placeQuestion(true);
        }else {
            wrongAnswers++;
            placeQuestion(false);
        }newQuestion();
    }

    public void placeQuestion(Boolean correct) throws JSONException {
        if(correct){
            if (language == "en")
                highlightQuestions.put("<font color=#1BCA13>" + obj.getString("QuestionEn") + "<br/>" + obj.getString("CorrectAnswerEn") + "</font><br/><br/>", correct);
            else
                highlightQuestions.put("<font color=#1BCA13>" + obj.getString("QuestionPt") + "<br/>" + obj.getString("CorrectAnswerPt") + "</font><br/><br/>", correct);
        }else{
        if (language == "en")
            highlightQuestions.put("<font color=#D81F1F>" + obj.getString("QuestionEn") + "<br/>" + obj.getString("WrongAnswerEn") + "</font><br/><br/>", correct);
        else
            highlightQuestions.put("<font color=#D81F1F>" + obj.getString("QuestionPt") + "<br/>" + obj.getString("WrongAnswerPt") + "</font><br/><br/>", correct);
        }
    }

    public void newQuestion() {
        Random rightOrLeft = new Random();
        Boolean choice = rightOrLeft.nextBoolean();



        if (counter > 0) {
            if(id==99){
                try {
                    random = new Random().nextInt(jArray.length());
                    obj = jArray.getJSONObject(random);
                    jArray.remove(random);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
            try {
                //
                random = new Random().nextInt(counter) + start;
                obj = jArray.getJSONObject(random);
                jArray.remove(random);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }

            try {
                if (language == "en")
                    question.setText(obj.getString("QuestionEn"));
                else
                    question.setText(obj.getString("QuestionPt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(choice){
            try {
                if (language == "en")
                    answerLeft.setText(obj.getString("CorrectAnswerEn"));
                else
                    answerLeft.setText(obj.getString("CorrectAnswerPt"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (language == "en")
                    answerRight.setText(obj.getString("WrongAnswerEn"));
                else
                    answerRight.setText(obj.getString("WrongAnswerPt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
                rightOrWrong = false;
            }else{
                try {
                    if (language == "en")
                        answerRight.setText(obj.getString("CorrectAnswerEn"));
                    else
                        answerRight.setText(obj.getString("CorrectAnswerPt"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (language == "en")
                        answerLeft.setText(obj.getString("WrongAnswerEn"));
                    else
                        answerLeft.setText(obj.getString("WrongAnswerPt"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rightOrWrong = true;
            }
        } else {
            Intent intent = new Intent(GameOngoing.this, MatchResult.class);
            intent.putExtra("score", correctAnswers);
            intent.putExtra("wrong", wrongAnswers);
            intent.putExtra("highlightedQuestions", highlightQuestions);
            intent.putExtra("regionID", id);
            startActivity(intent);
        }

    }
}