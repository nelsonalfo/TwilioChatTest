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
    private final int TYPE_MESSAGE_AFFILIATE = 1;
    private final int TYPE_STATUS = 2;

    private List<ChatMessage> messages;
    private TreeSet<Integer> statusMessageSet = new TreeSet<>();


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
        if (isNotEmpty(TokenFetcher.identity) && TokenFetcher.identity.equals(message.getAuthor())) {
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
        int statusMessageIndex = messages.size();

        statusMessageSet.add(statusMessageIndex);
        messages.add(message);

        notifyItemInserted(statusMessageIndex);
    }

    public void removeStatusMessage() {
        int statusMessageIndex = statusMessageSet.last();

        statusMessageSet.remove(statusMessageIndex);
        messages.remove(statusMessageIndex);

        notifyItemRemoved(statusMessageIndex);
    }

    @Override
    public int getItemViewType(int position) {

        if (statusMessageSet.contains(position)) {
            return TYPE_STATUS;
        }

        final UserMessage userMessage = (UserMessage) messages.get(position);
        if (userMessage.isClientTheAuthor()) {
            return TYPE_MESSAGE;
        }

        return TYPE_MESSAGE_AFFILIATE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;

        switch (viewType) {
            case TYPE_MESSAGE:
                itemView = layoutInflater.inflate(R.layout.message, parent, false);
                return new UserMessageViewHolder(itemView);
            case TYPE_MESSAGE_AFFILIATE:
                itemView = layoutInflater.inflate(R.layout.chat_message_affiliate, parent, false);
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
