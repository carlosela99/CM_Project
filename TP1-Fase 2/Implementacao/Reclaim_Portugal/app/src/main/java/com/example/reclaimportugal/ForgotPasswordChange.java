package com.example.reclaimportugal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class ForgotPasswordChange extends AppCompatActivity {

    private TextView failChange;

    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_change);

        isBusy = false;
        failChange = (TextView)findViewById(R.id.text_fail);
    }

    public void backToSubmitEmail(View v){
        startActivity(new Intent(ForgotPasswordChange.this, ForgotPassword.class));
    }

    public void changePassword(View v){
        EditText codeText = (EditText)findViewById(R.id.code_text);
        EditText passwordText = (EditText)findViewById(R.id.password_text);
        EditText passwordConfirmText = (EditText)findViewById(R.id.password_confirm_text);

        String code = codeText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordConfirm = passwordConfirmText.getText().toString();

        if (code.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || !password.equals(passwordConfirm)){
            failChange.setVisibility(View.VISIBLE);
        }
        else{
            failChange.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.forgotPasswordChange(code, password, passwordConfirm, this);
        }
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
        failChange.setVisibility(View.VISIBLE);
    }
}