package com.crm.customer.repository;

import com.crm.customer.model.Customer;
import com.crm.persistence.repository.CrudRepository;

public class CustomerRepository implements CrudRepository<Customer> {

    @Override
    public void save(Customer entity) {
        // Skeleton: persist customer
    }

    @Override
    public Customer findById(String id) {
        // Skeleton: fetch customer
        return null;
    }

    @Override
    public void update(Customer entity) {
        // Skeleton: update customer
    }

    @Override
    public void delete(String id) {
        // Skeleton: delete customer
    }
}
