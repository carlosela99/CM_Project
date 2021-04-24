package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private TextView failLogin;

    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isBusy = false;
        failLogin = (TextView)findViewById(R.id.text_fail);
    }

    public void showWelcome(View v){
        startActivity(new Intent(Login.this, Welcome.class));
    }

    public void showForgotPassword(View v){
        startActivity(new Intent(Login.this, Welcome.class));
    }

    private void showMainMenu(){
        startActivity(new Intent(Login.this, MainMenu.class));
        finish();
    }

    public void loginUser(View v){

        EditText userText = (EditText)findViewById(R.id.text_user);
        EditText passwordText = (EditText)findViewById(R.id.text_password);

        String user = userText.getText().toString();
        String password = passwordText.getText().toString();

        if (user.isEmpty() || password.isEmpty()){
            failLogin.setVisibility(View.VISIBLE);
        }
        else{
            failLogin.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.loginRequest(user, password, this);
        }
    }

    public void loginResult(JSONObject response){

        isBusy = false;
        Log.i("auth", "login success!");
        showMainMenu();
    }

    public void loginErrorResult(){

        isBusy = false;
        failLogin.setVisibility(View.VISIBLE);
    }
}