# 🛒 E-Commerce Microservices Backend

A scalable **Spring Boot Microservices** backend for an E-Commerce platform built using a **Database-per-Service architecture**. Each microservice is independently deployable, maintains its own MongoDB database, and exposes REST APIs for its domain.

---

# 📌 Features

- Microservices Architecture
- Independent MongoDB Database for every service
- RESTful APIs
- Layered Architecture
- DTO-based Request/Response
- Global Exception Handling
- Bean Validation
- Generic API Response Wrapper
- Human Readable IDs
- Enum-based Status Management
- Easy to Extend

---

# 🏗️ System Architecture

```
                   Client
                      │
      ─────────────────────────────────
                      │

        ┌──────── Product Service ───────┐
        │                                │
        ▼                                │
 Inventory Service                       │
        │                                │
        ▼                                │
     Cart Service                        │
        │                                │
        ▼                                │
     Order Service                       │
        │                                │
        ▼                                │
    Payment Service                      │
        │                                │
        ▼                                │
     Search Service                      │
```

Each service:

- Runs independently
- Has its own MongoDB database
- Can be deployed separately
- Follows the same project structure

---

# 🧰 Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Data MongoDB | Latest |
| MongoDB | 7.x |
| Maven | 3.8+ |
| Lombok | Latest |
| Jakarta Validation | Latest |

---

# 📁 Microservices

| Service | Port | MongoDB Database |
|---------|------|------------------|
| Product Service | **8081** | ecommerce_product_db |
| Inventory Service | **8082** | ecommerce_inventory_db |
| Cart Service | **8083** | ecommerce_cart_db |
| Order Service | **8084** | ecommerce_order_db |
| Payment Service | **8085** | ecommerce_payment_db |
| Search Service | **8086** | ecommerce_search_db |

---

# 📂 Project Structure

Every microservice follows the same clean architecture.

```
src
└── main
    ├── java
    │   └── com.ecommerce.{service}
    │
    ├── controller
    │      REST APIs
    │
    ├── service
    │      Business Interfaces
    │
    ├── service.impl
    │      Business Logic
    │
    ├── repository
    │      MongoDB Repositories
    │
    ├── model
    │      MongoDB Documents
    │
    ├── dto
    │      Request / Response Objects
    │
    └── exception
           Global Exception Handling
```

---

# ⚙️ Prerequisites

Before running the project, install:

- Java 21
- Maven 3.8+
- MongoDB Community Edition

Ensure MongoDB is running on

```
localhost:27017
```

---

# ▶️ Running the Services

Open a new terminal for every microservice.

```bash
cd product-service
mvn spring-boot:run
```

```bash
cd inventory-service
mvn spring-boot:run
```

```bash
cd cart-service
mvn spring-boot:run
```

```bash
cd order-service
mvn spring-boot:run
```

```bash
cd payment-service
mvn spring-boot:run
```

```bash
cd search-service
mvn spring-boot:run
```

---

# ❤️ Health Check APIs

| Service | Endpoint |
|----------|----------|
| Product | GET /api/products/health |
| Inventory | GET /api/inventory/health |
| Cart | GET /api/cart/health |
| Order | GET /api/orders/health |
| Payment | GET /api/payments/health |
| Search | GET /api/search/health |

Example

```
GET http://localhost:8081/api/products/health
```

---

# 📦 API Response Format

Every endpoint returns a standardized response.

```json
{
    "success": true,
    "message": "Operation Successful",
    "data": {}
}
```

---

# ✅ Validation

Incoming requests are validated using

- `@Valid`
- Jakarta Bean Validation
- DTO Classes

Example validations include:

- Not Blank
- Not Null
- Positive Numbers
- Valid Email
- Size Constraints

---

# 🚨 Exception Handling

A centralized `GlobalExceptionHandler` catches exceptions across the application and returns meaningful error responses.

Example:

```json
{
    "success": false,
    "message": "Product not found",
    "data": null
}
```

---

# 📋 Layered Architecture

Every request follows this flow:

```
Client

   │

Controller

   │

Service Interface

   │

Service Implementation

   │

Repository

   │

MongoDB
```

This separation improves

- Maintainability
- Testability
- Scalability
- Readability

---

# 🆔 Human Readable IDs

Instead of MongoDB ObjectIds, business entities use readable identifiers.

Example:

Orders

```
ORD-3A91F2C8
```

Payments

```
PAY-9FD123A8
```

---

# 📌 Enums Used

### Order Status

```
PENDING
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
```

### Payment Status

```
PENDING
SUCCESS
FAILED
REFUNDED
```

### Payment Mode

```
UPI
CARD
NET_BANKING
COD
WALLET
```

---

# 📖 API Documentation

Complete request and response examples are available in

```
POSTMAN_REQUESTS.md
```

---

# 📈 Design Highlights

✔ Database per Service

✔ RESTful API Design

✔ DTO Pattern

✔ Repository Pattern

✔ Generic API Response

✔ Global Exception Handling

✔ Bean Validation

✔ Layered Architecture

✔ Human Readable IDs

✔ Independent Deployable Services

---

# 🚀 Future Enhancements

- API Gateway
- Service Discovery (Eureka)
- Config Server
- Kafka/RabbitMQ Messaging
- JWT Authentication
- Docker & Docker Compose
- Kubernetes Deployment
- Redis Caching
- OpenAPI (Swagger)
- CI/CD Pipeline

---

# 👩‍💻 Author

**Lakshana Shree S**

Computer Science & Engineering

Java Full Stack Developer

```