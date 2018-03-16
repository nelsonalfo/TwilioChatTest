package com.example.miguelsoler.twiliochattest.Conexion;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.miguelsoler.twiliochattest.TwilioChatApplication;

import java.util.HashMap;

/****************************************************************************************
 * CREDITOS DE ARCHIVO:_________________________|__________________FECHA__________________
 * | # | DESARROLLADORES
 * |---|----------------------------------------|-----------------------------------------
 * | 1 |    Miguel Soler                        |                 02/03/2018.
 * |_____________________________________________________________________________________
 ***************************************************************************************/

public class SessionManager {
    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_TOKEN = "tokeb";
    public static final String KEY_IDENTITY = "identity";
    private static final String PREF_NAME = "TWILIOCHAT";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static SessionManager instance =
            new SessionManager(TwilioChatApplication.get().getApplicationContext());
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void createSession(String channel, String identity, String token ) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_CHANNEL, channel);
        editor.putString(KEY_IDENTITY, identity);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
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
        editor = editor.clear();
        editor.commit();
    }

    public boolean isLoggedChannelIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

}
