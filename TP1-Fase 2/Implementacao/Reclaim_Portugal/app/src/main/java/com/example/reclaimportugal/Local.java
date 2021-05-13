package com.example.reclaimportugal;

import android.content.Context;
import android.content.SharedPreferences;

public class Local {

    private static boolean loadBoolean(String tag, String namespace, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(tag, false);
    }

    private static void saveBoolean(String tag, String namespace, boolean value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putBoolean(tag, value);
        editor.apply();
    }

    private static int loadInt(String tag, String namespace, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(tag, 0);
    }

    private static void saveInt(String tag, String namespace, int value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putInt(tag, value);
        editor.apply();
    }

    private static String loadString(String tag, String namespace, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        return sharedPreferences.getString(tag, "");
    }

    private static void saveString(String tag, String namespace, String value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.putString(tag, value);
        editor.apply();
    }

    public static boolean getMusic(Context context){
        return loadBoolean("music", "settings", context);
    }

    public static void setMusic(boolean on, Context context){
        saveBoolean("music", "settings", on, context);
    }

    public static boolean getSound(Context context){
        return loadBoolean("sound", "settings", context);
    }

    public static void setSound(boolean on, Context context){
        saveBoolean("sound", "settings", on, context);
    }

    public static String getLanguage(Context context){
        return loadString("language", "settings", context);
    }

    public static void setLanguage(String language, Context context){
        saveString("language", "settings", language, context);
    }

    public static void setRegionScore(int score, String region, Context context){
        saveInt(region, "game", score, context);
    }

    public static int getRegionScore(String region, Context context){
        return loadInt(region, "game", context);
    }
}
