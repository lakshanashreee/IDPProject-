# E-Commerce Microservices Backend

## Architecture
6 independent Spring Boot microservices, each with its own MongoDB database.

| Service           | Port | Database                  |
|-------------------|------|---------------------------|
| product-service   | 8081 | ecommerce_product_db      |
| inventory-service | 8082 | ecommerce_inventory_db    |
| cart-service      | 8083 | ecommerce_cart_db         |
| order-service     | 8084 | ecommerce_order_db        |
| payment-service   | 8085 | ecommerce_payment_db      |
| search-service    | 8086 | ecommerce_search_db       |

## Prerequisites
- Java 21
- Maven 3.8+
- MongoDB running on localhost:27017

## Run Each Service
Open a separate terminal for each service:

```bash
cd product-service   && mvn spring-boot:run
cd inventory-service && mvn spring-boot:run
cd cart-service      && mvn spring-boot:run
cd order-service     && mvn spring-boot:run
cd payment-service   && mvn spring-boot:run
cd search-service    && mvn spring-boot:run
```

## Package Structure (same for all services)
```
src/main/java/com/ecommerce/{servicename}/
├── controller/         → REST endpoints
├── service/            → Interface definitions
│   └── impl/           → Business logic
├── repository/         → MongoDB data access
├── model/              → MongoDB documents
├── dto/                → Request & Response objects
│   ├── ApiResponse.java
│   ├── *Request.java
│   └── *Response.java
└── exception/          → Custom exceptions + GlobalExceptionHandler
```

## Health Checks
```
GET http://localhost:8081/api/products/health
GET http://localhost:8082/api/inventory/health
GET http://localhost:8083/api/cart/health
GET http://localhost:8084/api/orders/health
GET http://localhost:8085/api/payments/health
GET http://localhost:8086/api/search/health
```

## Key Design Decisions
- **ApiResponse<T>**: Every endpoint returns `{ success, message, data }` format
- **GlobalExceptionHandler**: Catches all exceptions and returns clean JSON
- **@Valid + DTOs**: Input validated before hitting service layer
- **Layered Architecture**: Controller → Service → Repository → MongoDB
- **Human-readable IDs**: Orders use ORD-XXXXXXXX, Payments use PAY-XXXXXXXX
- **Enums**: OrderStatus, PaymentMode, PaymentStatus for type safety

## See POSTMAN_REQUESTS.md for all API examples
