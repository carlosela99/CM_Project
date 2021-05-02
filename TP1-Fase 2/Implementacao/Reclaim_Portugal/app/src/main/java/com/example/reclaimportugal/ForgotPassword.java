package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

    private TextView failEmail;

    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        isBusy = false;
        failEmail = (TextView)findViewById(R.id.text_fail);
    }

    public void showLogin(View v){
        startActivity(new Intent(ForgotPassword.this, Login.class));
    }

    private void showChangeActivity(){
        startActivity(new Intent(ForgotPassword.this, ForgotPasswordChange.class));
    }

    public void confirmEmail(View v){

        EditText emailText = (EditText)findViewById(R.id.text_email);

        String email = emailText.getText().toString();

        if (email.isEmpty()){
            failEmail.setVisibility(View.VISIBLE);
        }
        else{
            failEmail.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.forgotPassword(email, this);
        }
    }

    public void forgotResult(JSONObject response){

        isBusy = false;
        showChangeActivity();
    }

    public void forgotErrorResult(){

        isBusy = false;
        failEmail.setVisibility(View.VISIBLE);
    }
}