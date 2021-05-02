package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

    private TextView failLogin;

    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    private void showCodeActivity(){
        startActivity(new Intent(ForgotPassword.this, ForgetPasswordCode.class));
    }

    public void forgotResult(JSONObject response){

        isBusy = false;
        showCodeActivity();
    }

    public void forgotErrorResult(){

        isBusy = false;
        failLogin.setVisibility(View.VISIBLE);
    }
}