package com.example.miguelsoler.twiliochattest.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.miguelsoler.twiliochattest.R;
import com.example.miguelsoler.twiliochattest.messages.ChatMessage;
import com.example.miguelsoler.twiliochattest.messages.UserMessage;
import com.example.miguelsoler.twiliochattest.utils.DateFormatter;


/**
 * Created by nelso on 19/3/2018.
 */

class UserMessageViewHolder extends RecyclerView.ViewHolder implements ViewBinder {
    private TextView textViewMessage;
    private TextView textViewAuthor;
    private TextView textViewDate;

    public UserMessageViewHolder(View itemView) {
        super(itemView);

        textViewMessage = itemView.findViewById(R.id.textViewMessage);
        textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
        textViewDate = itemView.findViewById(R.id.textViewDate);
    }

    public void bind(ChatMessage message) {
        boolean theClientIsTheAuthor = ((UserMessage) message).isClientTheAuthor();

        textViewMessage.setText(message.getMessageBody());
        textViewAuthor.setText(theClientIsTheAuthor ? "Me" : message.getAuthor());
        textViewDate.setText(DateFormatter.getFormattedDateFromISOString(message.getTimeStamp()));
    }
}
