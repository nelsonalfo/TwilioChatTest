package com.example.miguelsoler.twiliochattest.channels;

import com.twilio.chat.Channel;

import java.util.List;

public interface LoadChannelListener {

  void onChannelsFinishedLoading(List<Channel> channels);

}
