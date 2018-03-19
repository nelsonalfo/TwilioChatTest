package com.example.miguelsoler.twiliochattest;

import android.app.Application;

import com.example.miguelsoler.twiliochattest.chat.ChatClientManager;


/****************************************************************************************
 * CREDITOS DE ARCHIVO:_________________________|__________________FECHA__________________
 * | # | DESARROLLADORES
 * |---|----------------------------------------|-----------------------------------------
 * | 1 |    Miguel Soler                        |                 02/03/2018.
 * |_____________________________________________________________________________________
 ***************************************************************************************/

public class TwilioChatApplication extends Application {
    private static TwilioChatApplication instance;
    private ChatClientManager basicClient;

    public static TwilioChatApplication get() {
        return instance;
    }

    public ChatClientManager getChatClientManager() {
        return this.basicClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TwilioChatApplication.instance = this;
        basicClient = new ChatClientManager(getApplicationContext());
    }


}
