package com.example.miguelsoler.twiliochattest.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miguelsoler.twiliochattest.R;
import com.example.miguelsoler.twiliochattest.connection.TokenFetcher;
import com.example.miguelsoler.twiliochattest.messages.ChatMessage;
import com.example.miguelsoler.twiliochattest.messages.StatusMessage;
import com.example.miguelsoler.twiliochattest.messages.UserMessage;
import com.twilio.chat.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_MESSAGE = 0;
    private final int TYPE_STATUS = 1;

    private List<ChatMessage> messages;
    private TreeSet statusMessageSet = new TreeSet();


    public MessageAdapter() {
        messages = new ArrayList<>();
    }

    public void setMessages(List<Message> messages) {
        this.messages = convertTwilioMessages(messages);
        this.statusMessageSet.clear();
        notifyDataSetChanged();
    }

    private List<ChatMessage> convertTwilioMessages(List<Message> messages) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            addUserMessage(chatMessages, message);
        }
        return chatMessages;
    }

    private void addUserMessage(List<ChatMessage> chatMessages, Message message) {
        UserMessage userMessage = new UserMessage(message);
        if(isNotEmpty(TokenFetcher.identity) && TokenFetcher.identity.equals(message.getAuthor())){
            userMessage.setClientAsTheAuthor();
        }

        chatMessages.add(userMessage);
    }

    public void addMessage(Message message) {
        addUserMessage(messages, message);
        notifyDataSetChanged();
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public void addStatusMessage(StatusMessage message) {
        messages.add(message);
        statusMessageSet.add(messages.size() - 1);
        notifyItemInserted(messages.size());
    }

    @Override
    public int getItemViewType(int position) {
        return statusMessageSet.contains(position) ? TYPE_STATUS : TYPE_MESSAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;

        switch (viewType) {
            case TYPE_MESSAGE:
                itemView = layoutInflater.inflate(R.layout.message, parent, false);
                return new UserMessageViewHolder(itemView);
            case TYPE_STATUS:
                itemView = layoutInflater.inflate(R.layout.status_message, parent, false);
                return new MessageStatusViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewBinder binder = (ViewBinder) holder;
        binder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
