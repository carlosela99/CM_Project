package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private TextView failRegister;

    private String submittedEmail;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submittedEmail = null;
        isBusy = false;
        failRegister = (TextView)findViewById(R.id.text_fail);
    }

    public void showWelcome(View v){
        startActivity(new Intent(Register.this, Welcome.class));
    }

    private void showConfirm(){
        if (submittedEmail != null){
            Intent intent = new Intent(Register.this, RegisterConfirmCode.class);
            intent.putExtra("EMAIL_ADDRESS", submittedEmail);
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

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || !password.equals(confirmPassword)){
            failRegister.setVisibility(View.VISIBLE);
        }
        else{
            failRegister.setVisibility(View.INVISIBLE);
            isBusy = true;
            submittedEmail = email;
            Server.registerRequest(username, email, password, this);
        }
    }

    public void registerResult(JSONObject response){

        isBusy = false;
        Log.i("auth", "register success!");
        showConfirm();
    }

    public void registerErrorResult(){
        submittedEmail = null;
        isBusy = false;
        failRegister.setVisibility(View.VISIBLE);
    }
}