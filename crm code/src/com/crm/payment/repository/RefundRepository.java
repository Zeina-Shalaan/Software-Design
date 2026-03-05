package com.crm.payment.repository;

import com.crm.payment.model.Refund;
import com.crm.persistence.repository.CrudRepository;

public class RefundRepository implements CrudRepository<Refund> {

    @Override
    public void save(Refund entity) { }

    @Override
    public Refund findById(String id) { return null; }

    @Override
    public void update(Refund entity) { }

    @Override
    public void delete(String id) { }
}
