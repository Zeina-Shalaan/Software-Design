# Deliverable 1 — CRM System: Design Patterns Implementation

**Course:** Software Engineering 2 (SW2)  
**Submission:** Deliverable 1  
**Language:** Java  

---

## Overview

This deliverable demonstrates the implementation of three core **Gang of Four (GoF) Design Patterns** within a CRM (Customer Relationship Management) system:

| # | Pattern | Category | Where Used |
|---|---------|----------|------------|
| 1 | **Singleton** | Creational | `DatabaseConnectionManager` |
| 2 | **Factory Method** | Creational | `AlertController` hierarchy, `ReportFactory` hierarchy |
| 3 | **Abstract Factory** | Creational | `CommunicationFactory`, `PaymentFactory` |

All patterns are demonstrated via **Controllers** and **Repositories**, following a proper layered architecture.

---

## Project Structure

```
Deliverable 1/
├── README.md
└── src/
    └── com/
        └── crm/
            ├── Main.java                          ← Entry point — runs all tests
            │
            ├── common/                            ← Shared models & enums
            │   ├── Employee.java
            │   ├── Money.java
            │   ├── Address.java
            │   └── enums/
            │       ├── CustomerStatus.java
            │       ├── PaymentMethodType.java
            │       ├── PaymentStatus.java
            │       ├── MessageDirection.java
            │       ├── CommunicationChannel.java
            │       └── ... (other enums)
            │
            ├── persistence/                       ← SINGLETON PATTERN
            │   ├── DatabaseConnectionManager.java ← Singleton
            │   ├── SlaConfigurationManager.java   ← Singleton
            │   ├── StockThresholdManager.java     ← Singleton
            │   ├── SystemConfigurationManager.java← Singleton
            │   └── repository/
            │       └── CrudRepository.java        ← Generic CRUD interface
            │
            ├── alert/                             ← FACTORY METHOD PATTERN
            │   ├── factory/
            │   │   ├── AlertFactory.java          ← Abstract Factory (interface)
            │   │   ├── LowStockAlertFactory.java  ← Concrete Factory
            │   │   ├── DeliveryDelayAlertFactory.java
            │   │   └── SlaBreachAlertFactory.java
            │   ├── controller/
            │   │   ├── AlertController.java       ← Abstract Controller (Factory Method)
            │   │   ├── InventoryAlertController.java ← Concrete Controller
            │   │   ├── DeliveryDelayAlertController.java
            │   │   └── SlaBreachAlertController.java
            │   ├── model/
            │   │   ├── SystemAlert.java           ← Abstract product
            │   │   ├── LowStockAlert.java
            │   │   ├── DeliveryDelayAlert.java
            │   │   └── SlaBreachAlert.java
            │   └── repository/
            │       └── AlertRepository.java
            │
            ├── communication/                     ← ABSTRACT FACTORY PATTERN
            │   ├── Notification.java              ← Product interface
            │   ├── factory/
            │   │   ├── CommunicationFactory.java  ← Abstract Factory (interface)
            │   │   ├── SMSFactory.java            ← Concrete Factory
            │   │   ├── EmailFactory.java           ← Concrete Factory
            │   │   └── ChatFactory.java            ← Concrete Factory
            │   ├── channel/
            │   │   ├── CommunicationChannel.java  ← Product interface
            │   │   ├── SMS/ (SMSChannel, SmsMessage, SmsNotification)
            │   │   ├── Email/ (EmailChannel, EmailMessage, EmailNotification)
            │   │   └── Chat/ (ChatChannel, ChatMessage, ChatNotification)
            │   └── controller/
            │       └── CommunicationController.java ← Routes messages Employee↔Customer
            │
            ├── customer/                          ← Customer domain
            │   ├── model/
            │   │   ├── Customer.java
            │   │   ├── Message.java               ← Abstract product
            │   │   ├── Complaint.java
            │   │   └── ...
            │   ├── controller/
            │   │   ├── CustomerController.java
            │   │   ├── MessageController.java     ← Uses Abstract Factory
            │   │   └── ComplaintController.java
            │   └── repository/
            │       ├── CustomerRepository.java
            │       ├── MessageRepository.java
            │       └── ComplaintRepository.java
            │
            ├── payment/                           ← ABSTRACT FACTORY PATTERN
            │   ├── factory/
            │   │   ├── PaymentFactory.java        ← Abstract Factory (interface)
            │   │   ├── CardPaymentFactory.java    ← Concrete Factory
            │   │   └── CodPaymentFactory.java     ← Concrete Factory
            │   ├── model/
            │   │   ├── PaymentProcessor.java      ← Abstract product
            │   │   ├── PaymentReceipt.java        ← Abstract product
            │   │   ├── PaymentTransaction.java
            │   │   ├── CardProcessor.java
            │   │   ├── CardReceipt.java
            │   │   ├── CodProcessor.java
            │   │   └── CodReceipt.java
            │   ├── controller/
            │   │   └── PaymentController.java
            │   └── repository/
            │       ├── PaymentRepository.java
            │       └── RefundRepository.java
            │
            ├── reporting/                         ← FACTORY METHOD PATTERN
            │   ├── Report.java                    ← Abstract product
            │   ├── CustomerSummaryReport.java
            │   ├── OrderReport.java
            │   ├── WeeklyDeliveryReport.java
            │   ├── factory/
            │   │   ├── ReportFactory.java         ← Abstract Factory (interface)
            │   │   ├── CustomerReportFactory.java
            │   │   ├── OrderReportFactory.java
            │   │   └── WeeklyDeliveryReportFactory.java
            │   └── controller/
            │       └── ReportController.java
            │
            └── order/                             ← Order domain
                ├── model/ (Order, OrderItem, Product, Delivery)
                ├── controller/ (OrderController, DeliveryController)
                └── repository/ (OrderRepository, ProductRepository)
```

---

## Design Patterns Explained

### 1. Singleton Pattern
**File:** `persistence/DatabaseConnectionManager.java`

Ensures only **one** database connection instance exists throughout the application.

```
DatabaseConnectionManager.getInstance()  →  always returns the same object
```

> Also applied to: `SlaConfigurationManager`, `StockThresholdManager`, `SystemConfigurationManager`

---

### 2. Factory Method Pattern
**Files:** `alert/` and `reporting/`

Defines an interface for creating objects, but lets **subclasses decide** which class to instantiate.

```
AlertController (abstract)
    └── createAlert()           ← Factory Method
         ├── InventoryAlertController   → creates LowStockAlert
         ├── DeliveryDelayAlertController → creates DeliveryDelayAlert
         └── SlaBreachAlertController   → creates SlaBreachAlert
```

---

### 3. Abstract Factory Pattern
**Files:** `communication/` and `payment/`

Creates **families of related objects** without specifying their concrete classes.

```
CommunicationFactory (interface)
    ├── createChannel()      → SMSChannel / EmailChannel / ChatChannel
    ├── createMessage()      → SmsMessage / EmailMessage / ChatMessage
    └── createNotification() → SmsNotification / EmailNotification / ChatNotification

Implemented by: SMSFactory | EmailFactory | ChatFactory
```

---

## How to Run

### Compile
```bash
cd src
javac com/crm/Main.java
```

### Run
```bash
java com.crm.Main
```

### Expected Output
The program runs 5 test functions in sequence:
1. `testSingleton()` — verifies same instance is returned
2. `testFactoryMethod()` — creates and resolves an alert via `InventoryAlertController`
3. `testAbstractFactory()` — sends messages Employee ↔ Customer via SMS & Email factories
4. `testPaymentController()` — processes a card payment via `PaymentController`
5. `testReportController()` — generates a customer report via `ReportController`
