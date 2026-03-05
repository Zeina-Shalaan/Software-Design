package com.crm.order.repository;

import com.crm.order.model.Product;
import com.crm.persistence.repository.CrudRepository;

public class ProductRepository implements CrudRepository<Product> {

    @Override
    public void save(Product entity) { }

    @Override
    public Product findById(String id) { return null; }

    @Override
    public void update(Product entity) { }

    @Override
    public void delete(String id) { }
}
