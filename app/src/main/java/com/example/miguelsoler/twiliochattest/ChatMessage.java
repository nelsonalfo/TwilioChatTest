package com.example.miguelsoler.twiliochattest;

public interface ChatMessage {

  String getMessageBody();

  String getAuthor();

  String getTimeStamp();
}
