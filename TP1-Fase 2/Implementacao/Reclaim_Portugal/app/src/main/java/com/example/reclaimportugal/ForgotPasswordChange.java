package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class ForgotPasswordChange extends AppCompatActivity {

    private TextView failLogin;

    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_change);

        isBusy = false;
        failLogin = (TextView)findViewById(R.id.text_fail);
    }

    private void showConfirmationActivity(){
        startActivity(new Intent(ForgotPasswordChange.this, ForgotPasswordComplete.class));
    }

    public void changePasswordResult(JSONObject response){

        isBusy = false;
        showConfirmationActivity();
    }

    public void changePasswordErrorResult(){

        isBusy = false;
        failLogin.setVisibility(View.VISIBLE);
    }
}