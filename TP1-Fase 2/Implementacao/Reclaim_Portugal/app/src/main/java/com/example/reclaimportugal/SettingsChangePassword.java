package com.example.reclaimportugal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class SettingsChangePassword extends AppCompatActivity {

    private TextView failChange;

    private String email;
    private boolean isBusy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_password);

        isBusy = false;
        failChange = (TextView)findViewById(R.id.text_fail);
        email = getIntent().getStringExtra("EMAIL");

        ImageButton imageButton = (ImageButton) findViewById(R.id.settingsChangePasswordBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
    }

    private void openSettings() {
        finish();
    }

    public void changePassword(View v){
        EditText oldPasswordText = (EditText)findViewById(R.id.OldPassword);
        EditText newPasswordText = (EditText)findViewById(R.id.NewPassword);
        EditText newPasswordConfirmText = (EditText)findViewById(R.id.ConfirmNewPassword);

        String oldPassword = oldPasswordText.getText().toString();
        String newPassword = newPasswordText.getText().toString();
        String newPasswordConfirm = newPasswordConfirmText.getText().toString();

        if (oldPassword.isEmpty()){
            showErrorMessage(getString(R.string.invalid_old_password));
        }
        else if (!Validations.PasswordFormat(newPassword)){
            showErrorMessage(getString(R.string.invalid_password));
        }
        else if (!newPassword.equals(newPasswordConfirm)){
            showErrorMessage(getString(R.string.invalid_password_compare));
        }
        else{
            failChange.setVisibility(View.INVISIBLE);
            isBusy = true;
            Server.changePasswordSettings(email, oldPassword, newPassword, this);
        }
    }

    private void showErrorMessage(String message){
        failChange.setText(message);
        failChange.setVisibility(View.VISIBLE);
    }

    private void showConfirmationActivity(){
        startActivity(new Intent(this, SettingsConfirmation.class));
    }

    public void changePasswordResult(JSONObject response){

        isBusy = false;
        showConfirmationActivity();
    }

    public void changePasswordErrorResult(){

        isBusy = false;
        showErrorMessage(getString(R.string.invalid_old_password));
    }
}
