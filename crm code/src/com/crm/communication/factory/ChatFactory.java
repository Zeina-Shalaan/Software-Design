package com.crm.communication.factory;

import com.crm.communication.channel.ChatChannel;
import com.crm.communication.channel.CommunicationChannel;

public class ChatFactory implements CommunicationFactory {
    @Override
    public CommunicationChannel createChannel() {
        return new ChatChannel();
    }
}
