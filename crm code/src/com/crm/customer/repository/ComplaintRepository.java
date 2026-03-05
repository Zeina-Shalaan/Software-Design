package com.crm.customer.repository;

import com.crm.customer.model.Complaint;
import com.crm.persistence.repository.CrudRepository;

public class ComplaintRepository implements CrudRepository<Complaint> {

    @Override
    public void save(Complaint entity) { }

    @Override
    public Complaint findById(String id) { return null; }

    @Override
    public void update(Complaint entity) { }

    @Override
    public void delete(String id) { }
}
