package com.crm;

import com.crm.alert.controller.AlertController;
import com.crm.alert.factory.AlertFactory;
import com.crm.alert.factory.LowStockAlertFactory;
import com.crm.alert.model.SystemAlert;
import com.crm.alert.repository.AlertRepository;
import com.crm.common.Money;
import com.crm.common.enums.PaymentMethodType;

import com.crm.payment.model.PaymentTransaction;
import com.crm.persistence.DatabaseConnectionManager;

import static com.crm.common.enums.PaymentMethodType.Card;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionManager.getInstance().connect();
        System.out.println("CRM System Started...");
        DatabaseConnectionManager.getInstance().disconnect();

        Money amount = new Money(23.1, "USD");
        PaymentTransaction p1 = new PaymentTransaction("123", "Ord12", amount, Card);
        System.out.println(p1.getMethod());

        // --- Demo: Refactored Alert Factory (Factory Method Pattern) ---
        AlertRepository alertRepo = new AlertRepository();
        AlertController alertController = new AlertController(alertRepo);

        // We pass the concrete factory (LowStockAlertFactory) to the controller
        AlertFactory lowStockFactory = new LowStockAlertFactory();
        SystemAlert alert = alertController.createAlert(lowStockFactory, "ALT-001", "PROD-99");

        System.out.println("Created Alert: " + alert.getClass().getSimpleName() + " for ID: " + alert.getAlertId());

    }
}
