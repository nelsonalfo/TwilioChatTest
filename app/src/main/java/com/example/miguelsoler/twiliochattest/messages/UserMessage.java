package com.example.miguelsoler.twiliochattest.messages;

import com.twilio.chat.Message;


public class UserMessage implements ChatMessage {

    private String author = "";
    private String timeStamp = "";
    private String messageBody = "";
    private boolean clientIsTheAuthor;

    public UserMessage(Message message) {
        this.author = message.getAuthor();
        this.timeStamp = message.getTimeStamp();
        this.messageBody = message.getMessageBody();
    }

    public void setClientAsTheAuthor() {
        this.clientIsTheAuthor = true;
    }

    public boolean isClientTheAuthor() {
        return clientIsTheAuthor;
    }

    @Override
    public String getMessageBody() {
        return messageBody;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTimeStamp() {
        return timeStamp;
    }
}
