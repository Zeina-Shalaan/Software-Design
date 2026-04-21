# CRM System Design Patterns Report

This report explains the design patterns utilized throughout the CRM codebase, specifically mapped to the options available in `Main.java`'s `runInteractiveConsole()` entry point and its underlying functions. Recent refactoring incorporates domain-centric terminology (e.g., Providers, Policies, Listeners) for classic Gang of Four (GoF) patterns.

---

## 1. Customer Support View

The `customerSupportMenu()` groups functionalities for handling customer data, segmentation, complaints, and communication.

### Design Patterns Used:
*   **Mediator (`ComplaintEscalationHandlerService`)**
    *   **Where Used:** In `supportEscalateComplaint()`.
    *   **Details:** Encapsulates the complex interaction between `ComplaintController`, `SlaBreachAlertController`, and `CommunicationController`. Instead of these components calling each other directly during an SLA breach, the Mediator centralizes the escalation workflow, keeping the controllers loosely coupled.
*   **Strategy / Policy (`SlaCalculator` & `SegmentationPolicy`)**
    *   **Where Used:** In `supportCreateComplaint()` and `supportTestSegmentation()`.
    *   **Details:** When creating a complaint, the user selects a runtime policy (e.g., `StandardSlaCalculator`, `PriorityBasedSlaCalculator`, `VipSlaCalculator`) to calculate deadlines dynamically. Similarly, `GeographicSegmentation`, `SpendingBasedSegmentation`, and `ActivityBasedSegmentation` allow interchangeable customer grouping rules without altering the core Customer models.
*   **Abstract Factory / Provider (`CommunicationChannelProvider`)**
    *   **Where Used:** In `supportSendMessage()` and `supportEscalateComplaint()`.
    *   **Details:** Abstractions like `SmsChannelProvider` and `EmailChannelProvider` produce communication channels dynamically without hardcoding instantiations, making the system extensible to new messaging platforms.
*   **Decorator / Extension (`SystemAlert` wrappers)**
    *   **Where Used:** In `supportCreateDecoratedSlaAlert()`.
    *   **Details:** Adds behaviors to SLA alerts at runtime by wrapping them in `LoggedAlert`, `EscalatingAlert`, and `RetryingAlert` decorators. This prevents a combinatorial explosion of alert subclasses.

### Missing / Not Used Patterns:
*   **Builder:** `Customer` and `Complaint` entities are instantiated via verbose constructors spanning multiple parameters. A Builder pattern would streamline this, ensuring cleaner domain object creation.
*   **Command:** The "Edit customer info" functionality relies on unstructured sequential updates. The Command pattern would package editing requests as objects, allowing for an "Undo" feature for customer support agents.

---

## 2. Inventory Subsystem View

The `inventoryMenu()` provides tools for stock tracking, product management, and supplier integration.

### Design Patterns Used:
*   **Adapter (`ErpSupplierConnector`)**
    *   **Where Used:** In `inventoryFetchSupplierFromErp()` (which triggers `supplierController.importFromErp`).
    *   **Details:** Acts as a bridge between the CRM's internal `SupplierRepository` interface and a hypothetical external ERP system, converting data formats so both systems can communicate without tightly coupling the CRM to the ERP's API.
*   **Observer / Listener (`InventoryEventManager`)**
    *   **Where Used:** Implicitly triggered during `inventoryDeductStock()`.
    *   **Details:** `InventoryEventManager` serves as a publisher. When stock drops below thresholds, observers (`InventoryAlertController`, `SupplierController`, `InventoryController`) are automatically notified through the event manager to raise low stock alerts or generate automated purchase orders.
*   **Flyweight (`ProductCreator`)**
    *   **Where Used:** During system seeding and inherently used when updating/retrieving product details.
    *   **Details:** Reduces memory footprint by caching intrinsic product data (names, descriptions, prices) so duplicate product instances aren't instantiated repeatedly.

### Missing / Not Used Patterns:
*   **State:** The lifecycle of a `PurchaseOrder` is simple boolean flags (e.g., `isReceived()`). Utilizing the State pattern would better enforce the rules governing transitions (e.g., Draft -> Sent -> Received).
*   **Prototype:** Re-ordering from suppliers usually involves identical Purchase Orders. A Prototype pattern would easily allow users to clone an existing historical `PurchaseOrder` instead of creating one from scratch through the interactive console.

---

## 3. Orders & Payments

The `ordersPaymentsMenu()` drives the revenue-generating side of the CRM: taking orders, resolving payments, and updating fulfillment states.

### Design Patterns Used:
*   **Mediator (`OrderFulfillment`)**
    *   **Where Used:** In `mediatorCheckoutFlow()`.
    *   **Details:** Similar to the complaint mediator, this coordinates the checkout process. It handles order creation, initiates the corresponding payment transaction, attaches delivery details, and sends a notification, all without the underlying controllers cross-referencing one another.
*   **Iterator (`highValueItemIterator`)**
    *   **Where Used:** In `ordersViewItemsWithIterator()`.
    *   **Details:** Offers a secure way to traverse the elements of an `Order`'s items. Crucially, the custom `highValueItemIterator` encapsulates a filtering algorithm right into the traversal, hiding standard loop filtering logic from the client.
*   **Adapter (`StripePaymentProvider`)**
    *   **Where Used:** In `mediatorCheckoutFlow()` (when routing 'Stripe' payment method type).
    *   **Details:** Normalizes an external payment gateway (Stripe) to fit the internal `PaymentProvider` interface.
*   **Template Method (`PaymentProcessor`)**
    *   **Where Used:** Executed implicitly behind the scenes when completing payments (`PaymentController.processPayment`).
    *   **Details:** Defines the skeleton of a payment lifecycle (Validate -> Authorize -> Capture -> Generate Receipt), allowing subclasses (`Card`, `COD`) to override just the "Authorize" and "Capture" steps while forcing compliance with the overall algorithm.

### Missing / Not Used Patterns:
*   **State:** Order states (`Processing`, `Cancelled`, `Delivered`) are modified using straightforward setter logic inside `ordersUpdateStatusFromPayment()`. The State pattern would restrict invalid state jumps (e.g., moving a Cancelled order to Delivered).
*   **Memento:** Payment flows occasionally fail (e.g., network timeout during card capture). A Memento pattern would allow the system to snapshot the `Order` cart state prior to the transaction phase, ensuring flawless rollbacks.

---

## 4. Reports

The `reportsMenu()` deals with data synthesis and export.

### Design Patterns Used:
*   **Factory Method (`ReportGenerator`)**
    *   **Where Used:** In `generateReportWithRenderer()`.
    *   **Details:** Polymorphically instantiates specific reports—`CustomerReportGenerator`, `OrderReportGenerator`, and `WeeklyDeliveryReportGenerator`. The client requests a report without worrying about the specifics of its assembly.
*   **Bridge (`Report` & `ReportRenderer`)**
    *   **Where Used:** In `generateReportWithRenderer()`.
    *   **Details:** The `Report` logic (domain data retrieval) is developed independently of how it looks. The Bridge connects a Report to a rendering implementation (`ConsoleReportRenderer` or `CsvReportRenderer`), so you can add PDF rendering later without modifying the Report classes.

### Missing / Not Used Patterns:
*   **Composite:** In an enterprise environment, reports are often hierarchical. A Composite pattern would allow a "Global Summary Report" to automatically aggregate "Regional Reports", which themselves aggregate "Customer Reports".
*   **Facade:** Instead of exposing the Report/Renderer mapping combinations directly to the Main loop, a Reporting Facade could provide a simplified "One-Click Report" API.

---

## 5. Run Full Demo

The `runDemoSuite()` executes automated integration tests and showcases additional system-wide patterns not strictly bound to one interactive menu.

### System-Wide Design Patterns Used:
*   **Singleton (`DatabaseConnectionManager`, `InventoryEventManager`, `SystemConfigurationManager`)**
    *   **Where Used:** Globally across repositories and core configuration controllers.
    *   **Details:** Ensures that resource-heavy components like mock-database connections or event routers have only a single, shared instance throughout the lifetime of the JVM application.
*   **Decorator / Extension (`Delivery` wrappers)**
    *   **Where Used:** In `testDecoratorPatternDelivery()`.
    *   **Details:** Dynamically attaches `AlertingDelivery`, `NotifyingDelivery`, and `LoggedDelivery` behaviors to a base `Delivery` entity. This permits independent logistics upgrades without complex subclassing.
