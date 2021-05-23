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

    private String email;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        isBusy = false;
        failEmail = (TextView)findViewById(R.id.text_fail);
    }

    public void showLogin(View v){
        finish();
    }

    private void showChangeActivity(){
        Intent intent = new Intent(ForgotPassword.this, ForgotPasswordChange.class);
        intent.putExtra("EMAIL", email);
        finish();
        startActivity(intent);
    }

    public void confirmEmail(View v){
        if (isBusy){
            return;
        }
        EditText emailText = (EditText)findViewById(R.id.text_email);

        email = emailText.getText().toString();

        if (!Validations.EmailFormat(email)){
            showErrorMessage(getString(R.string.invalid_email));
        }
        else{
            failEmail.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.forgotPassword(email, this);
        }
    }

    private void showErrorMessage(String message){
        failEmail.setText(message);
        failEmail.setVisibility(View.VISIBLE);
    }

    public void forgotResult(JSONObject response){

        isBusy = false;
        showChangeActivity();
    }

    public void forgotErrorResult(){

        isBusy = false;
        showErrorMessage(getString(R.string.error_email_not_exists));
    }
}