package com.crm.communication.factory;

import com.crm.communication.channel.CommunicationChannel;
import com.crm.communication.channel.SMSChannel;

public class SMSFactory implements CommunicationFactory {
    @Override
    public CommunicationChannel createChannel() {
        return new SMSChannel();
    }
}
