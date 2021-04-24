package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class RegisterConfirmCode extends AppCompatActivity {

    private TextView failConfirmation;

    private String submittedEmail;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm_code);

        isBusy = false;
        submittedEmail = getIntent().getStringExtra("EMAIL_ADDRESS");
        failConfirmation = (TextView)findViewById(R.id.text_fail);
    }

    public void backToForm(View v){
        startActivity(new Intent(RegisterConfirmCode.this, Register.class));
    }

    private void showComplete(){
        startActivity(new Intent(RegisterConfirmCode.this, RegisterComplete.class));
    }

    public void confirmCode(View v){

        EditText codeText = (EditText)findViewById(R.id.text_code);

        String code = codeText.getText().toString();

        if (code.isEmpty() || code.length() != 5 || submittedEmail == null){
            failConfirmation.setVisibility(View.VISIBLE);
        }
        else{
            failConfirmation.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.confirmRegisterRequest(submittedEmail, code, this);
        }
    }

    public void registerConfirmationResult(JSONObject response){

        isBusy = false;
        Log.i("auth", "register success!");
        showComplete();
    }

    public void registerConfirmationErrorResult(){

        isBusy = false;
        failConfirmation.setVisibility(View.VISIBLE);
    }
}