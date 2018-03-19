package com.example.miguelsoler.twiliochattest.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.miguelsoler.twiliochattest.R;
import com.example.miguelsoler.twiliochattest.messages.ChatMessage;


/**
 * Created by nelso on 19/3/2018.
 */

public class MessageStatusViewHolder extends RecyclerView.ViewHolder implements ViewBinder {
    private TextView textViewStatus;


    public MessageStatusViewHolder(View itemView) {
        super(itemView);

        textViewStatus = itemView.findViewById(R.id.textViewStatusMessage);
    }

    public void bind(ChatMessage status){
        String statusMessage = status.getMessageBody();
        textViewStatus.setText(statusMessage);
    }
}
