package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SubmitQuestion extends AppCompatActivity {
    private ImageButton imageButton;

    private TextView failChange;

    private String email;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_question);

        imageButton = (ImageButton) findViewById(R.id.submitQuestionBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void submitQuestion(View v){
        EditText questionText = (EditText)findViewById(R.id.questionText);
        EditText correctAnswerText = (EditText)findViewById(R.id.corretAnswer);
        EditText wrongAnswerText = (EditText)findViewById(R.id.wrongAnswer);

        String question = questionText.getText().toString();
        String correctAnswer = correctAnswerText.getText().toString();
        String wrongAnswer = wrongAnswerText.getText().toString();

        if (question.isEmpty()){
            //showErrorMessage(getString(R.string.mandatory_code));
        }
        else if (correctAnswer.isEmpty()){
            //showErrorMessage(getString(R.string.invalid_password));
        }
        else if (wrongAnswer.isEmpty()){
            //showErrorMessage(getString(R.string.invalid_password_compare));
        }
        else{
            failChange.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.submitQuestion(email, question, correctAnswer, wrongAnswer, this);
        }
    }

    private void showErrorMessage(String message){
        failChange.setText(message);
        failChange.setVisibility(View.VISIBLE);
    }

    private void showConfirmationActivity(){
        Intent intent = new Intent(SubmitQuestion.this, SubmitQuestionComplete.class);
        finish();
        startActivity(intent);
    }

    public void submitQuestionResult(JSONObject response){

        isBusy = false;
        showConfirmationActivity();
    }

    public void submitQuestioErrorResult(){

        isBusy = false;
        //showErrorMessage(getString(R.string.invalid_code));
    }
}
