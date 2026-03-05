package com.crm.customer.controller;

import com.crm.customer.model.Message;
import com.crm.customer.repository.MessageRepository;

public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    public Message getMessage(String messageId) {
        return messageRepository.findById(messageId);
    }

    public void updateMessage(Message message) {
        messageRepository.update(message);
    }

    public void deleteMessage(String messageId) {
        messageRepository.delete(messageId);
    }
}
