package com.crm.alert.controller;

import com.crm.alert.model.SystemAlert;
import com.crm.alert.repository.AlertRepository;

public abstract class AlertController {
    protected final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    // This is the Abstract Factory Method
    protected abstract SystemAlert createAlert(String id, String ref);

    // This is the operation that uses the factory method
    public SystemAlert processAlert(String id, String ref) {
        SystemAlert alert = createAlert(id, ref);
        alertRepository.save(id, alert);
        return alert;
    }

    public void resolveAlert(String alertId) {
        SystemAlert a = alertRepository.findById(alertId);
        if (a != null) {
            a.resolve();
            alertRepository.update(alertId, a);
        }
    }
}
