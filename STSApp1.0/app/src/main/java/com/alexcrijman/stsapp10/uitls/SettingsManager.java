package com.alexcrijman.stsapp10.uitls;


import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
    private static final String APP_SETTINGS = "APP_SETTINGS";
    private static final String MAIL_VALUE = "mail_user";


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String getSomeStringValue(Context context) {
        return getSharedPreferences(context).getString(MAIL_VALUE, null);
    }

    public static void setSomeStringValue(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(MAIL_VALUE, newValue);
        editor.commit();
    }
}

