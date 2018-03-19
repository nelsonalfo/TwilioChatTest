package com.example.miguelsoler.twiliochattest.connection;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


/****************************************************************************************
 * CREDITOS DE ARCHIVO:_________________________|__________________FECHA__________________
 * | # | DESARROLLADORES
 * |---|----------------------------------------|-----------------------------------------
 * | 1 |    Miguel Soler                        |                 02/03/2018.
 * |_____________________________________________________________________________________
 ***************************************************************************************/

public class SessionManager {
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_TOKEN = "tokeb";
    private static final String KEY_IDENTITY = "identity";
    private static final String PREF_NAME = "TWILIOCHAT";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final int PRIVATE_MODE = 0;

    private static SessionManager instance;

    private SharedPreferences pref;


    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }

        return instance;
    }

    public void createSession(String channel, String identity, String token) {
        final SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_CHANNEL, channel);
        editor.putString(KEY_IDENTITY, identity);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public HashMap<String, String> getChannelDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_CHANNEL, pref.getString(KEY_CHANNEL, null));

        return user;
    }

    public String getChannel() {
        return pref.getString(KEY_CHANNEL, null);
    }

    public String getidentity() {
        return pref.getString(KEY_IDENTITY, null);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void cleanChannel() {
        SharedPreferences.Editor editor = pref.edit();
        editor = editor.clear();
        editor.apply();
    }

    public boolean isLoggedChannelIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

}
