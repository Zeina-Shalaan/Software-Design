package com.crm.alert.controller;

import com.crm.alert.factory.AlertFactory;
import com.crm.alert.model.SystemAlert;
import com.crm.alert.repository.AlertRepository;

public class AlertController {
    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public SystemAlert createAlert(AlertFactory factory, String alertId, String referenceId) {
        SystemAlert alert = factory.createAlert(alertId, referenceId);
        alertRepository.save(alert);
        return alert;
    }

    public void resolveAlert(String alertId) {
        SystemAlert a = alertRepository.findById(alertId);
        if (a != null) {
            a.resolve();
            alertRepository.update(a);
        }
    }
}
