package com.example.reclaimportugal;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    public static boolean EmailFormat(String input){
        return (!TextUtils.isEmpty(input) && Patterns.EMAIL_ADDRESS.matcher(input).matches());
    }

    public static boolean PasswordFormat(String input){

        /*if (!input.isEmpty() && !compare.isEmpty() && input.length() >= 8 && input.equals(compare)){
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(input);

            return matcher.matches();
        }
        return false;*/

        return !input.isEmpty() && input.length() >= 6;
    }
}
