package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
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
        finish();
    }

    public void showForgotPassword(View v){
        startActivity(new Intent(Login.this, ForgotPassword.class));
    }

    private void showMainMenu(String username, String email){
        Intent intent = new Intent(Login.this, MainMenu.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
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

        try{
            String username = response.getString("username");
            String email = response.getString("email");

            showMainMenu(username, email);
        }
        catch (JSONException e) {
            failLogin.setVisibility(View.VISIBLE);
        }
    }

    public void loginErrorResult(){

        isBusy = false;
        failLogin.setVisibility(View.VISIBLE);
    }
}