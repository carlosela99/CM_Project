package com.example.reclaimportugal;

import android.content.Context;
import android.content.SharedPreferences;

public class Local {

    private static boolean loadBoolean(String tag, boolean default_value, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(tag, default_value);
    }

    private static void saveBoolean(String tag, boolean value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putBoolean(tag, value);
        editor.apply();
    }

    private static String loadString(String tag, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences.getString(tag, "");
    }

    private static void saveString(String tag, String value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putString(tag, value);
        editor.apply();
    }

    private static int loadInt(String tag, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(tag, 0);
    }

    private static void saveInt(String tag, int value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putInt(tag, value);
        editor.apply();
    }

    public static boolean getMusic(Context context){
        return loadBoolean("music", true, context);
    }

    public static void setMusic(boolean on, Context context){
        saveBoolean("music", on, context);
    }

    public static boolean getSound(Context context){
        return loadBoolean("sound", true, context);
    }

    public static void setSound(boolean on, Context context){
        saveBoolean("sound", on, context);
    }

    public static String getLanguage(Context context){
        return loadString("language", context);
    }

    public static void setLanguage(String language, Context context){
        saveString("language", language, context);
    }

    public static void setRegionScore(int score, String region, Context context){
        if (getRegionScore(region, context) < score){
            saveInt(region, score, context);
        }
    }

    public static int getRegionScore(String region, Context context){
        return loadInt(region, context);
    }

    public static int getGamesCount(Context context){
        int value = loadInt("games_count", context);
        return value;
    }

    public static void incrementGamesCount(Context context){
        saveInt("games_count", getGamesCount(context) + 1, context);
    }

    public static int getCorrectAnswersCount(Context context){
        return loadInt("correct_answers_count", context);
    }

    public static void incrementCorrectAnswersCount(int value, Context context){
        saveInt("correct_answers_count", getCorrectAnswersCount(context) + value, context);
    }

    public static int getWrongAnswersCount(Context context){
        return loadInt("wrong_answers_count", context);
    }

    public static void incrementWrongAnswersCount(int value, Context context){
        saveInt("wrong_answers_count", getWrongAnswersCount(context) + value, context);
    }
}
