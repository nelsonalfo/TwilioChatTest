package com.example.miguelsoler.twiliochattest.Models;

/****************************************************************************************
 * CREDITOS DE ARCHIVO:_________________________|__________________FECHA_________________
 * | # | DESARROLLADORES
 * |---|----------------------------------------|----------------------------------------
 * | 1 |    Miguel Soler                        |                 02/03/2018.
 * |_____________________________________________________________________________________
 ***************************************************************************************/

public class Channels {
    private String Token;
    private String identity;
    private String channel;

    public Channels() {
    }

    public Channels(String token, String identity, String channel) {
        Token = token;
        this.identity = identity;
        this.channel = channel;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
