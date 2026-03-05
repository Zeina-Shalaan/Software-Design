package com.crm.communication.controller;

import com.crm.communication.channel.CommunicationChannel;
import com.crm.communication.factory.CommunicationFactory;

public class CommunicationController {
    public CommunicationChannel selectChannel(CommunicationFactory factory) {
        return factory.createChannel();
    }
}
