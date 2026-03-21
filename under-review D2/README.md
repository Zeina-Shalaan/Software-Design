# Deliverable 2 — CRM System: Design Patterns Implementation

## Overview

This deliverable demonstrates the implementation of core and advanced **Gang of Four (GoF) Design Patterns** within a robust CRM (Customer Relationship Management) system:

| # | Pattern | Category | Where Used |
|---|---------|----------|------------|
| 1 | **Singleton** | Creational | `DatabaseConnectionManager` |
| 2 | **Factory Method** | Creational | `AlertController` and `ReportFactory` hierarchies |
| 3 | **Abstract Factory** | Creational | `CommunicationFactory`, `PaymentFactory` |
| 4 | **Decorator** | Structural | `SystemAlert` behavior extensions, `Delivery` status tracking |
| 5 | **Adapter** | Structural | `ErpSupplierAdapter` (ERP system), `StripePaymentAdapter` |
| 6 | **Bridge** | Structural | `Report`/`Renderer` separation, `ChannelNotification` |
| 7 | **Flyweight** | Creational | `ProductFactory` (managing shared Product objects) |

All patterns are integrated into a layered architecture using **Controllers**, **Repositories**, and **Models** to ensure **SOLID** compliance.

---

## Project Structure

```
crm-code/
├── README.md                              ← Project documentation
├── pom.xml                                ← Maven configuration (Lombok & Build)
└── src/
    └── com/
        └── crm/
            ├── Main.java                  ← Entry point — runs the test suite
            │
            ├── common/                    ← Shared models & Utility
            │   ├── Employee.java
            │   ├── Money.java 
            │   ├── Address.java
            │   └── enums/                 ← Centralized system enums
            │
            ├── persistence/               ← SINGLETON PATTERN
            │   └── DatabaseConnectionManager.java
            │
            ├── alert/                     ← FACTORY METHOD & DECORATOR
            │   ├── factory/               ← Factory classes
            │   ├── controller/            ← Alert orchestration
            │   ├── model/                 ← Base Alerts & Decorators
            │   └── repository/            ← Alert persistence
            │
            ├── communication/             ← ABSTRACT FACTORY & BRIDGE
            │   ├── factory/               ← Message factories (SMS/Email)
            │   ├── channel/               ← Implementation bridges
            │   └── controller/            ← Communication logic
            │
            ├── customer/                  ← Customer Domain
            │   ├── controller/            ← Segmenation & Complaint logic
            │   ├── model/                 ← Customer & Complaint models
            │   └── repository/            ← Customer persistence
            │
            ├── inventory/                 ← FLYWEIGHT, ADAPTER, & CONTROLLER
            │   ├── controller/            ← Product & Inventory management
            │   ├── model/                 ← Products & PurchaseOrders
            │   ├── service/               ← ERP Adapter services
            │   └── repository/            ← Inventory persistence
            │
            ├── order/                     ← DECORATOR & CONTROLLER
            │   ├── model/                 ← Order & Delivery (Decorated)
            │   └── controller/            ← Order management logic
            │
            ├── payment/                   ← ABSTRACT FACTORY & ADAPTER
            │   ├── factory/               ← Payment method factories
            │   ├── model/                 ← Processors & Stripe Adapter
            │   └── controller/            ← Payment transaction logic
            │
            └── reporting/                 ← FACTORY METHOD & BRIDGE
                ├── factory/               ← Report generation factories
                ├── model/                 ← Report types & Renderers
                └── controller/            ← Report orchestration logic
```

---

## Design Patterns Explained

### 1. Creational Patterns
*   **Singleton (`DatabaseConnectionManager`)**: Ensures a single database connection point.
*   **Factory Method (`AlertFactory`)**: Subclasses decide which specific alert (`LowStock`, `SlaBreach`) to instantiate.
*   **Abstract Factory (`CommunicationFactory`)**: Creates families of related objects (Channels, Notifications) for SMS, Email, or Chat.
*   **Flyweight (`ProductFactory`)**: Reuses existing `Product` objects to reduce memory overhead for shared inventory items.

### 2. Structural Patterns
*   **Decorator (`LoggingAlertDecorator`, `RetryAlertDecorator`, `EscalatingAlertDecorator`, `AlertingDeliveryDecorator`)**: Dynamically adds behavior like logging, notifications, and automated retries to existing objects without creating a complex hierarchy of subclasses.

    #### **Case Study: Cross-Cutting Behavior for SystemAlert**
    
    **The Problem (Before Decorator)**
    The CRM needed to add "cross-cutting" behaviors—like **Logging**, **Retrying**, and **Escalation**—to various alert types (`LowStockAlert`, `SlaBreachAlert`). 
    
    If we used standard **Inheritance**, we would face a "Class Explosion": 
    * To have a logged *and* retried LowStockAlert, we'd need `LoggingRetryingLowStockAlert`. 
    * We would need similar combinations for every other alert type, leading to hundreds of redundant classes. 
    * Static inheritance prevents us from adding behaviors dynamically at runtime based on specific conditions.
    
    **The Solution (After Decorator)**
    By using the **Decorator Pattern**, we decoupled the core alert logic from its optional behaviors:
    *   **Dynamic Stacking**: We can now "wrap" any alert with any combination of behaviors at runtime (e.g., `new LoggingAlertDecorator(new RetryAlertDecorator(new SlaBreachAlert()))`).
    *   **Single Responsibility**: Each decorator handles exactly one concern (only logging, only retrying, etc.), making the code modular and easy to maintain.
    *   **Clean Core**: Core alerts like `LowStockAlert` remain simple and focused only on their specific data.

*   **Adapter (`ErpSupplierAdapter`, `StripePaymentAdapter`)**: Allows the CRM to communicate with external systems (ERP and Payment Gateways) by translating incompatible interfaces into a standardized internal format.

    #### **Case Study: External Payment Gateway (Stripe Adapter)**
    
    **The Problem (Before Adapter)**
    The CRM's internal payment system was designed to work with a standardized `PaymentProcessor` interface that:
    1.  Accepts a custom `Money` object (containing both amount and currency).
    2.  Provides a generic `processPayment` method.
    
    However, we needed to integrate **Stripe**, a popular external payment gateway. Stripe's proprietary client library (`StripeClient`) has a completely different signature:
    *   It requires the amount to be passed as a `double` representing **cents** (e.g., $10.50 must be sent as `1050`).
    *   It requires the currency as a separate `String` code.
    *   Its method is named `charge()`, which doesn't match our internal `processPayment()` naming convention.
    
    **Without the Adapter**, we would have been forced to tightly couple our core CRM logic to Stripe's specific API, making it difficult to switch providers and violating the **Open/Closed Principle**.
    
    **The Solution (After Adapter)**
    By implementing the `StripePaymentAdapter`, we successfully bridged these two incompatible interfaces:
    *   **Interface Mapping**: The adapter implements the CRM's `PaymentProcessor` while wrapping the `StripeClient`.
    *   **Data Transformation**: The adapter automatically converts the `Money` object into cents and extracts the currency code.
    *   **Decoupling**: The CRM continues to call `processPayment(money)` generically, completely unaware that it is communicating with Stripe.

*   **Bridge (`Report`/`Renderer`)**: Decouples the report type (e.g., `OrderReport`) from how it is displayed (e.g., `CsvReportRenderer`).

---



## Expected Output Test Suites
The system executes a unified test suite in `Main.java` demonstrating:
1.  **Singleton Check**: Verifies instance consistency.
2.  **Alert Demo**: Shows Factory creation + nested Decorators (Retry, Log, Escalate).
3.  **Reporting Demo**: Shows Bridge switching between Console and CSV renderers.
4.  **Customer Demo**: Demonstrates Segmentation and Abstract Factory communication.
5.  **Inventory Demo**: Demonstrates Flyweight reuse and automated Inventory Alerts.
6.  **Payment Demo**: Shows Abstract Factory creation and the Stripe Adapter.
7.  **ERP Demo**: Demonstrates the Supplier Adapter importing data to the CRM.
8.  **Delivery Demo**: Shows nested Decorators tracking delivery status.
9.  **Communication Bridge**: Verifies final Bridge implementation for SMS/Email channels.
