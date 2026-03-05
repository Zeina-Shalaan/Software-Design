package com.crm.customer.repository;

import com.crm.customer.model.Message;
import com.crm.persistence.repository.CrudRepository;

public class MessageRepository implements CrudRepository<Message> {

    @Override
    public void save(Message entity) { }

    @Override
    public Message findById(String id) { return null; }

    @Override
    public void update(Message entity) { }

    @Override
    public void delete(String id) { }
}
