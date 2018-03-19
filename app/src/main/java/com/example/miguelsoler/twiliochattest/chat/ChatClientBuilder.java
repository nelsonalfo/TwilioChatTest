package com.example.miguelsoler.twiliochattest.chat;

import android.content.Context;

import com.example.miguelsoler.twiliochattest.listeners.TaskCompletionListener;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;

public class ChatClientBuilder extends CallbackListener<ChatClient> {

  private Context context;
  private TaskCompletionListener<ChatClient, String> buildListener;

  public ChatClientBuilder(Context context) {
    this.context = context;
  }

  public void build(String token, final TaskCompletionListener<ChatClient, String> listener) {
    ChatClient.Properties properties =new ChatClient.Properties.Builder().createProperties();

    this.buildListener = listener;
    ChatClient.create(context.getApplicationContext(), token, properties, this);
  }


  @Override
  public void onSuccess(ChatClient chatClient) {
    this.buildListener.onSuccess(chatClient);
  }

  @Override
  public void onError(ErrorInfo errorInfo) {
    this.buildListener.onError(errorInfo.getMessage());
  }
}
