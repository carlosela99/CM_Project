package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

    private TextView failLogin;

    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        isBusy = false;
        failLogin = (TextView)findViewById(R.id.text_fail);
    }

    private void showChangeActivity(){
        startActivity(new Intent(ForgotPassword.this, ForgotPasswordChange.class));
    }

    public void forgotResult(JSONObject response){

        isBusy = false;
        showChangeActivity();
    }

    public void forgotErrorResult(){

        isBusy = false;
        failLogin.setVisibility(View.VISIBLE);
    }
}