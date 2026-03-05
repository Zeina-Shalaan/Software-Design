package com.crm.alert.repository;

import com.crm.alert.model.SystemAlert;
import com.crm.persistence.repository.CrudRepository;

public class AlertRepository implements CrudRepository<SystemAlert> {

    @Override
    public void save(SystemAlert entity) { }

    @Override
    public SystemAlert findById(String id) { return null; }

    @Override
    public void update(SystemAlert entity) { }

    @Override
    public void delete(String id) { }
}
