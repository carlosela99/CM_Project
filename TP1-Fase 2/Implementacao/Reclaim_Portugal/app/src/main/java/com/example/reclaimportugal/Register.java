package com.example.reclaimportugal;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private TextView failRegister;

    private String submittedEmail;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // load language from user settings
        Translations.loadLanguage(getApplicationContext(), getResources());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submittedEmail = null;
        isBusy = false;
        failRegister = (TextView)findViewById(R.id.text_fail);
    }

    public void showWelcome(View v){
        finish();
    }

    private void showConfirm(){
        if (submittedEmail != null){
            Intent intent = new Intent(Register.this, RegisterConfirmCode.class);
            intent.putExtra("EMAIL_ADDRESS", submittedEmail);
            finish();
            startActivity(intent);
        }
    }

    public void registerUser(View v){

        EditText usernameText = (EditText)findViewById(R.id.text_username);
        EditText emailText = (EditText)findViewById(R.id.text_email);
        EditText passwordText = (EditText)findViewById(R.id.text_password);
        EditText confirmPasswordText = (EditText)findViewById(R.id.text_password_confirm);

        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        if (username.isEmpty()){
            showErrorMessage(getString(R.string.invalid_username));
        }
        else if (!Validations.EmailFormat(email)){
            showErrorMessage(getString(R.string.invalid_email));
        }
        else if (!Validations.PasswordFormat(password)){
            showErrorMessage(getString(R.string.invalid_password));
        }
        else if (!password.equals(confirmPassword)){
            showErrorMessage(getString(R.string.invalid_password_compare));
        }
        else{
            failRegister.setVisibility(View.INVISIBLE);
            isBusy = true;
            submittedEmail = email;
            Server.registerRequest(username, email, password, this);
        }
    }

    private void showErrorMessage(String message){
        failRegister.setText(message);
        failRegister.setVisibility(View.VISIBLE);
    }

    public void registerResult(JSONObject response){

        isBusy = false;
        Log.i("auth", "register success!");
        showConfirm();
    }

    public void registerErrorResult(){
        submittedEmail = null;
        isBusy = false;
        showErrorMessage(getString(R.string.invalid_register));
    }
}