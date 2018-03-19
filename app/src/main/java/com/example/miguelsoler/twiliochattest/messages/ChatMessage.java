package com.example.miguelsoler.twiliochattest.messages;

public interface ChatMessage {

  String getMessageBody();

  String getAuthor();

  String getTimeStamp();
}
