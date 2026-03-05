package com.crm.payment.repository;

import com.crm.payment.model.PaymentTransaction;
import com.crm.persistence.repository.CrudRepository;

public class PaymentRepository implements CrudRepository<PaymentTransaction> {

    @Override
    public void save(PaymentTransaction entity) { }

    @Override
    public PaymentTransaction findById(String id) { return null; }

    @Override
    public void update(PaymentTransaction entity) { }

    @Override
    public void delete(String id) { }
}
