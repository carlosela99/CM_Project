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

    private String email;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_change);

        isBusy = false;
        failChange = (TextView)findViewById(R.id.text_fail);
        email = getIntent().getStringExtra("EMAIL");
    }

    public void backToLogin(View v){
        finish();
    }

    public void changePassword(View v){
        EditText codeText = (EditText)findViewById(R.id.code_text);
        EditText passwordText = (EditText)findViewById(R.id.password_text);
        EditText passwordConfirmText = (EditText)findViewById(R.id.password_confirm_text);

        String code = codeText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordConfirm = passwordConfirmText.getText().toString();

        if (code.isEmpty()){
            showErrorMessage(getString(R.string.mandatory_code));
        }
        else if (!Validations.PasswordFormat(password)){
            showErrorMessage(getString(R.string.invalid_password));
        }
        else if (!password.equals(passwordConfirm)){
            showErrorMessage(getString(R.string.invalid_password_compare));
        }
        else{
            failChange.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.forgotPasswordChange(email, code, password, this);
        }
    }

    private void showErrorMessage(String message){
        failChange.setText(message);
        failChange.setVisibility(View.VISIBLE);
    }

    private void showConfirmationActivity(){
        Intent intent = new Intent(ForgotPasswordChange.this, ForgotPasswordComplete.class);
        finish();
        startActivity(intent);
    }

    public void changePasswordResult(JSONObject response){

        isBusy = false;
        showConfirmationActivity();
    }

    public void changePasswordErrorResult(){

        isBusy = false;
        showErrorMessage(getString(R.string.invalid_code));
    }
}