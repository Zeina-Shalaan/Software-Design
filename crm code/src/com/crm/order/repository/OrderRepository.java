package com.crm.order.repository;

import com.crm.order.model.Order;
import com.crm.persistence.repository.CrudRepository;

public class OrderRepository implements CrudRepository<Order> {

    @Override
    public void save(Order entity) { }

    @Override
    public Order findById(String id) { return null; }

    @Override
    public void update(Order entity) { }

    @Override
    public void delete(String id) { }
}
