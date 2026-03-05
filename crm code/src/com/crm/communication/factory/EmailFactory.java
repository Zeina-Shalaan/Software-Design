package com.crm.communication.factory;

import com.crm.communication.channel.CommunicationChannel;
import com.crm.communication.channel.EmailChannel;

public class EmailFactory implements CommunicationFactory {
    @Override
    public CommunicationChannel createChannel() {
        return new EmailChannel();
    }
}
