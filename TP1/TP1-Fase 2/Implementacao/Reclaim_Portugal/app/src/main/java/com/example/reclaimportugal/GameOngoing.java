package com.example.reclaimportugal;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class GameOngoing extends AppCompatActivity implements SensorEventListener {
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
    private int nQuestions;

    private boolean isPlaying;
    private boolean sensorInput;
    private CountDownTimer sensorTimer;

    private SensorManager sensorManager;
    double axis;

    private final static float ACCELETOMETER_TRIGGER_VALUE = 1.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ongoing);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        //get the id of the region in specific so we can know which questions to get
        id = getIntent().getIntExtra("regionIDGame", -1);

        //if id is 99 then user selected quickmatch which contains all questions, else code goes to find the questions which belong to a certain id
        if(id == 99){
            try {
                jArray = new JSONArray(getJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //quickmatch will have 30 questions needed to answer within the time limit
            counter = 30;
        }else{
        try {
            JSONArray tempJArray;
            tempJArray = new JSONArray(getJson());
            jArray = new JSONArray();
            for(int x = 0; x < tempJArray.length(); x++){
                if(tempJArray.getJSONObject(x).getInt("RegionID") == id){
                    jArray.put(tempJArray.getJSONObject(x));
                }
            }
            //since some regions have more questions than others we use the length of the jArray to know how many questions there are
            counter = jArray.length();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        }

        nQuestions = counter;
        language = Locale.getDefault().getLanguage();
        countdownText = findViewById(R.id.countdown);
        question = findViewById(R.id.gameQuestion);
        answerLeft = findViewById(R.id.answerLeft);
        answerRight = findViewById(R.id.answerRight);

        //so we know which questions he got right and which he got wrong
        highlightQuestions = new HashMap<>();

        //method to trigger questions / the game itself
        newQuestion();

        //this button will trigger the popup
        pauseButton = findViewById(R.id.pauseGame);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v);
            }
        });
        isPlaying = true;
        sensorInput = true;
        startTimer();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1){

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (isPlaying && event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            if (sensorInput){
                if (axis - event.values[1] > ACCELETOMETER_TRIGGER_VALUE){
                    try {
                        chooseRight();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (axis - event.values[1] < -ACCELETOMETER_TRIGGER_VALUE){
                    try {
                        chooseLeft();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            //Log.i("sensor", String.valueOf(event.values[0]));
            //Log.i("sensor", String.valueOf(event.values[1]));
            //Log.i("sensor", String.valueOf(event.values[2]));
            axis = event.values[1];
        }
    }

    //begins the timer for the quiz
    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilli = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
        timerRunning = true;
    }

    public void startSensorTimer() {
        sensorInput = false;
        sensorTimer = new CountDownTimer(800, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                sensorInput = true;
            }
        }.start();
    }

    //updates timer
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

    //Brings out the pop-up
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

    //dismisses the popup
    public void continueGame(View v) {
        popupWindow.dismiss();
    }

    //if users clicks on quit in popup then goes to results
    public void quitGame(View v) {
        endGame();
    }

    //fetches all questions
    public String getJson() {
        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
            return json;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //left answer
    private void chooseLeft() throws JSONException {
        startSensorTimer();
        SoundManager.ClickSound(getApplicationContext());
        counter--;
        if(!rightOrWrong){
            correctAnswers++;
            placeQuestion(true);
        }else {
            wrongAnswers++;
            placeQuestion(false);
        }
        newQuestion();
    }

    //right answer
    private void chooseRight() throws JSONException {
        startSensorTimer();
        SoundManager.ClickSound(getApplicationContext());
        counter--;
        if(rightOrWrong) {
            correctAnswers++;
            placeQuestion(true);
        }else {
            wrongAnswers++;
            placeQuestion(false);
        }newQuestion();
    }

    //left answer
    public void chooseLeft(View v) throws JSONException {
        if (isPlaying){
            chooseLeft();
        }
    }

    //right answer
    public void chooseRight(View v) throws JSONException {
        if (isPlaying){
            chooseRight();
        }
    }

    //places the questions into the arraylist
    public void placeQuestion(Boolean correct) throws JSONException {
        if(correct){
            if (language.equals("en"))
                highlightQuestions.put("<font color=#1BCA13>" + obj.getString("QuestionEn") + "<br/> -" + obj.getString("CorrectAnswerEn") + "</font><br/><br/>", correct);
            else
                highlightQuestions.put("<font color=#1BCA13>" + obj.getString("QuestionPt") + "<br/> -" + obj.getString("CorrectAnswerPt") + "</font><br/><br/>", correct);
        }else{
        if (language.equals("en"))
            highlightQuestions.put("<font color=#D81F1F>" + obj.getString("QuestionEn") + "<br/> -" + obj.getString("WrongAnswerEn") + "</font><br/><br/>", correct);
        else
            highlightQuestions.put("<font color=#D81F1F>" + obj.getString("QuestionPt") + "<br/> -" + obj.getString("WrongAnswerPt") + "</font><br/><br/>", correct);
        }
    }

    //calls a new question
    public void newQuestion() {
        Random rightOrLeft = new Random();
        boolean choice = rightOrLeft.nextBoolean();

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
                random = new Random().nextInt(counter);
                obj = jArray.getJSONObject(random);
                jArray.remove(random);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }

            try {
                if (language.equals("en"))
                    question.setText(obj.getString("QuestionEn"));
                else
                    question.setText(obj.getString("QuestionPt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(choice){
            try {
                if (language.equals("en"))
                    answerLeft.setText(obj.getString("CorrectAnswerEn"));
                else
                    answerLeft.setText(obj.getString("CorrectAnswerPt"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (language.equals("en"))
                    answerRight.setText(obj.getString("WrongAnswerEn"));
                else
                    answerRight.setText(obj.getString("WrongAnswerPt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
                rightOrWrong = false;
            }else{
                try {
                    if (language.equals("en"))
                        answerRight.setText(obj.getString("CorrectAnswerEn"));
                    else
                        answerRight.setText(obj.getString("CorrectAnswerPt"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (language.equals("en"))
                        answerLeft.setText(obj.getString("WrongAnswerEn"));
                    else
                        answerLeft.setText(obj.getString("WrongAnswerPt"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rightOrWrong = true;
            }
        } else {
            endGame();
        }
    }

    public void endGame(){
        countDownTimer.cancel();

        if (!isPlaying)
            return;

        isPlaying = false;
        Intent intent = new Intent(GameOngoing.this, MatchResult.class);
        intent.putExtra("score", correctAnswers);
        intent.putExtra("wrong", wrongAnswers);
        intent.putExtra("highlightedQuestions", highlightQuestions);
        intent.putExtra("regionID", id);
        intent.putExtra("numberQuestions", nQuestions);
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}