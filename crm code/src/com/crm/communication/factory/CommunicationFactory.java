package com.crm.communication.factory;

import com.crm.communication.channel.CommunicationChannel;

public interface CommunicationFactory {
    CommunicationChannel createChannel();
}
