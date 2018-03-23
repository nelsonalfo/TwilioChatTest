package com.example.miguelsoler.twiliochattest.messages;

/**
 * Created by nelso on 22/3/2018.
 */

public class TypingStatusMessage extends StatusMessage {

    public TypingStatusMessage(String author) {
        super(author);
    }

    @Override
    public String getMessageBody() {
        return this.getAuthor() + " is typing";
    }

}
