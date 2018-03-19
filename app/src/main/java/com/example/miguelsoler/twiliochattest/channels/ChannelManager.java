package com.example.miguelsoler.twiliochattest.channels;

import android.os.Handler;
import android.os.Looper;

import com.example.miguelsoler.twiliochattest.chat.ChatClientManager;
import com.example.miguelsoler.twiliochattest.listeners.TaskCompletionListener;
import com.example.miguelsoler.twiliochattest.view.adapter.CustomChannelComparator;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelDescriptor;
import com.twilio.chat.Channels;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ChatClientListener;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Paginator;
import com.twilio.chat.StatusListener;
import com.twilio.chat.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ChannelManager implements ChatClientListener {
    private ChatClientManager chatClientManager;
    private ChannelExtractor channelExtractor;
    private List<Channel> channels;
    private Channels channelsObject;
    private ChatClientListener listener;
    private Handler handler;
    private Boolean isRefreshingChannels = false;

    public ChannelManager(ChatClientManager chatClientManager) {
        this.chatClientManager = chatClientManager;
        this.channelExtractor = new ChannelExtractor();
        this.listener = this;

        handler = setupListenerHandler();
    }


    private Handler setupListenerHandler() {
        Looper looper;
        Handler handler;

        if ((looper = Looper.myLooper()) != null) {
            handler = new Handler(looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            handler = new Handler(looper);
        } else {
            throw new IllegalArgumentException("Channel Listener must have a Looper.");
        }

        return handler;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void populateChannels(final LoadChannelListener listener) {
        if (this.chatClientManager == null || this.isRefreshingChannels) {
            return;
        }

        this.isRefreshingChannels = true;

        handler.post(new Runnable() {
            @Override
            public void run() {
                channelsObject = chatClientManager.getChatClient().getChannels();

                channelsObject.getUserChannelsList(new CallbackListener<Paginator<ChannelDescriptor>>() {
                    @Override
                    public void onSuccess(Paginator<ChannelDescriptor> channelDescriptorPaginator) {
                        extractChannelsFromPaginatorAndPopulate(channelDescriptorPaginator, listener);
                    }
                });

            }
        });
    }

    private void extractChannelsFromPaginatorAndPopulate(final Paginator<ChannelDescriptor> paginator, final LoadChannelListener listener) {
        channels = new ArrayList<>();
        ChannelManager.this.channels.clear();
        channelExtractor.extractAndSortFromChannelDescriptor(paginator, new TaskCompletionListener<List<Channel>, String>() {
                    @Override
                    public void onSuccess(List<Channel> channels) {
                        ChannelManager.this.channels.addAll(channels);
                        Collections.sort(ChannelManager.this.channels, new CustomChannelComparator());
                        ChannelManager.this.isRefreshingChannels = false;
                        chatClientManager.setClientListener(ChannelManager.this);
                        listener.onChannelsFinishedLoading(ChannelManager.this.channels);
                    }

                    @Override
                    public void onError(String errorText) {
                        System.out.println("Error populating channels: " + errorText);
                    }
                });
    }

    @Override
    public void onChannelSynchronizationChange(Channel channel) {
        if (listener != null) {
            listener.onChannelSynchronizationChange(channel);
        }
    }

    @Override
    public void onError(ErrorInfo errorInfo) {
        if (listener != null) {
            listener.onError(errorInfo);
        }
    }

    public void leaveChannelWithHandler(Channel channel, StatusListener handler) {
        channel.leave(handler);
    }

    public void deleteChannelWithHandler(Channel channel, StatusListener handler) {
        channel.destroy(handler);
    }

    public void setChannelListener(ChatClientListener listener) {
        this.listener = listener;
    }

    @Override
    public void onChannelJoined(Channel channel) {

    }

    @Override
    public void onChannelInvited(Channel channel) {

    }

    @Override
    public void onChannelAdded(Channel channel) {

    }

    @Override
    public void onChannelUpdated(Channel channel, Channel.UpdateReason updateReason) {

    }

    @Override
    public void onChannelDeleted(Channel channel) {

    }

    @Override
    public void onUserUpdated(User user, User.UpdateReason updateReason) {

    }

    @Override
    public void onUserSubscribed(User user) {

    }

    @Override
    public void onUserUnsubscribed(User user) {

    }

    @Override
    public void onClientSynchronization(ChatClient.SynchronizationStatus synchronizationStatus) {

    }

    @Override
    public void onNotification(String s, String s1) {

    }

    @Override
    public void onNotificationSubscribed() {

    }

    @Override
    public void onNotificationFailed(ErrorInfo errorInfo) {

    }

    @Override
    public void onConnectionStateChange(ChatClient.ConnectionState connectionState) {

    }

}
