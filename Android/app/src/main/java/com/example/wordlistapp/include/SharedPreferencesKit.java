package com.example.wordlistapp.include;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesKit {

    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = context.getSharedPreferences("shared", MODE_PRIVATE);
    }

    public static int loadInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public static String loadString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public static float loadFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    public static void writeInt(String key, int val) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key, val);
        editor.apply();
    }

    public static void writeString(String key, String val) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, val);
        editor.apply();
    }

    public static void writeFloat(String key, float val) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(key, val);
        editor.apply();
    }

}
