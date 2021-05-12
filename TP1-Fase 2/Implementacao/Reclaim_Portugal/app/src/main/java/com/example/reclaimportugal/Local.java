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
        editor.putBoolean(tag, value);
        editor.commit();
    }

    private static String loadString(String tag, String namespace, Context app_context) {
        SharedPreferences sharedPreferences = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        return sharedPreferences.getString(tag, "");
    }

    private static void saveString(String tag, String namespace, String value, Context app_context){
        SharedPreferences sharedPref = app_context.getSharedPreferences(namespace, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, value);
        editor.commit();
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
}
