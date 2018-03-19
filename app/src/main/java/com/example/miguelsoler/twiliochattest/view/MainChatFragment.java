package com.example.miguelsoler.twiliochattest.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miguelsoler.twiliochattest.R;
import com.example.miguelsoler.twiliochattest.messages.JoinedStatusMessage;
import com.example.miguelsoler.twiliochattest.messages.LeftStatusMessage;
import com.example.miguelsoler.twiliochattest.messages.StatusMessage;
import com.example.miguelsoler.twiliochattest.view.adapter.MessageAdapter;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.Messages;
import com.twilio.chat.StatusListener;

import java.util.List;


public class MainChatFragment extends Fragment implements ChannelListener {
    Button sendButton;
    RecyclerView messagesRecyclerView;
    EditText messageTextEdit;

    MessageAdapter messageAdapter;
    Channel currentChannel;
    Messages messagesObject;

    public MainChatFragment() {
    }

    public static MainChatFragment newInstance() {
        return new MainChatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_chat, container, false);

        sendButton = view.findViewById(R.id.buttonSend);
        messagesRecyclerView = view.findViewById(R.id.listViewMessages);
        messageTextEdit = view.findViewById(R.id.editTextMessage);

        setUpRecyclerView();

        setUpListeners();

        setMessageInputEnabled(false);

        return view;
    }

    private void setUpRecyclerView() {
        messageAdapter = new MessageAdapter();
        messagesRecyclerView.setAdapter(messageAdapter);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        messagesRecyclerView.setLayoutManager(layoutManager);

        messagesRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    messagesRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollToLastMessage();
                        }
                    }, 100);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel, final StatusListener handler) {
        if (currentChannel == null) {
            this.currentChannel = null;
            return;
        }

        if (!currentChannel.equals(this.currentChannel)) {
            setMessageInputEnabled(false);

            this.currentChannel = currentChannel;
            this.currentChannel.addListener(this);

            if (this.currentChannel.getStatus() == Channel.ChannelStatus.JOINED) {
                loadMessages(handler);
            } else {
                this.currentChannel.join(new StatusListener() {
                    @Override
                    public void onSuccess() {
                        loadMessages(handler);
                    }

                    @Override
                    public void onError(ErrorInfo errorInfo) {
                    }
                });
            }
        }
    }

    private void loadMessages(final StatusListener handler) {
        this.messagesObject = this.currentChannel.getMessages();

        if (messagesObject != null) {
            messagesObject.getLastMessages(100, new CallbackListener<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messageList) {
                    messageAdapter.setMessages(messageList);
                    scrollToLastMessage();
                    setMessageInputEnabled(true);
                    messageTextEdit.requestFocus();
                    handler.onSuccess();
                }
            });
        }
    }

    private void scrollToLastMessage() {
        int lastPosition = messageAdapter.getItemCount() - 1;
        messagesRecyclerView.smoothScrollToPosition(lastPosition);
    }

    private void setUpListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        messageTextEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }

    private void sendMessage() {
        String messageText = getTextInput();
        if (messageText.length() == 0) {
            return;
        }

        Message.Options newMessage = Message.options().withBody(messageText);
        this.messagesObject.sendMessage(newMessage, null);
        clearTextInput();
    }

    private void setMessageInputEnabled(final boolean enabled) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainChatFragment.this.sendButton.setEnabled(enabled);
                MainChatFragment.this.messageTextEdit.setEnabled(enabled);
            }
        });
    }

    private String getTextInput() {
        return messageTextEdit.getText().toString();
    }

    private void clearTextInput() {
        messageTextEdit.setText("");
    }


    @Override
    public void onMessageAdded(Message message) {
        messageAdapter.addMessage(message);

        scrollToLastMessage();
    }

    @Override
    public void onMessageUpdated(Message message, Message.UpdateReason updateReason) {

    }

    @Override
    public void onMessageDeleted(Message message) {

    }

    @Override
    public void onMemberAdded(Member member) {
        StatusMessage statusMessage = new JoinedStatusMessage(member.getIdentity());
        this.messageAdapter.addStatusMessage(statusMessage);
    }

    @Override
    public void onMemberUpdated(Member member, Member.UpdateReason updateReason) {

    }

    @Override
    public void onMemberDeleted(Member member) {
        StatusMessage statusMessage = new LeftStatusMessage(member.getIdentity());
        this.messageAdapter.addStatusMessage(statusMessage);
    }

    @Override
    public void onTypingStarted(Member member) {
    }

    @Override
    public void onTypingEnded(Member member) {
    }

    @Override
    public void onSynchronizationChanged(Channel channel) {

    }
}
