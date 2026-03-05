package com.crm.customer.controller;

import com.crm.customer.model.Customer;
import com.crm.customer.repository.CustomerRepository;

public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer retrieveCustomer(String customerId) {
        return customerRepository.findById(customerId);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    public void deleteCustomer(String customerId) {
        customerRepository.delete(customerId);
    }
}
