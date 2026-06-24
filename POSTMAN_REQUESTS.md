# =====================================================
# POSTMAN REQUESTS - ALL 6 MICROSERVICES
# =====================================================

## HOW TO RUN EACH SERVICE
# Open a terminal for each service and run:
cd C:\Users\lakshana.shree\LAXXU\ECOMMERCE\product-service   && mvn spring-boot:run
cd C:\Users\lakshana.shree\LAXXU\ECOMMERCE\inventory-service && mvn spring-boot:run
cd C:\Users\lakshana.shree\LAXXU\ECOMMERCE\cart-service      && mvn spring-boot:run
cd C:\Users\lakshana.shree\LAXXU\ECOMMERCE\order-service     && mvn spring-boot:run
cd C:\Users\lakshana.shree\LAXXU\ECOMMERCE\payment-service   && mvn spring-boot:run
cd C:\Users\lakshana.shree\LAXXU\ECOMMERCE\search-service    && mvn spring-boot:run

# Prerequisites: MongoDB must be running on localhost:27017

# =====================================================
# 1. PRODUCT SERVICE (port 8081)
# =====================================================

### Health Check
GET http://localhost:8081/api/products/health

### Create Product
POST http://localhost:8081/api/products
Content-Type: application/json
{
  "name": "Samsung Galaxy S24",
  "description": "Latest Samsung flagship smartphone",
  "category": "Electronics",
  "price": 79999.00,
  "quantity": 50,
  "active": true
}

### Get All Products
GET http://localhost:8081/api/products

### Get Product by ID
GET http://localhost:8081/api/products/{id}
# Replace {id} with the MongoDB _id returned from Create Product

### Get Products by Category
GET http://localhost:8081/api/products/category/Electronics

### Update Product
PUT http://localhost:8081/api/products/{id}
Content-Type: application/json
{
  "name": "Samsung Galaxy S24 Ultra",
  "description": "Updated model with AI features",
  "category": "Electronics",
  "price": 89999.00,
  "quantity": 30,
  "active": true
}

### Delete Product
DELETE http://localhost:8081/api/products/{id}


# =====================================================
# 2. INVENTORY SERVICE (port 8082)
# =====================================================

### Health Check
GET http://localhost:8082/api/inventory/health

### Create Inventory Entry
POST http://localhost:8082/api/inventory
Content-Type: application/json
{
  "productId": "prod-001",
  "availableQuantity": 100,
  "reservedQuantity": 0,
  "lowStockThreshold": 10
}

### Get All Inventory
GET http://localhost:8082/api/inventory

### Get Inventory by Product ID
GET http://localhost:8082/api/inventory/prod-001

### Add Stock
PUT http://localhost:8082/api/inventory/prod-001/add-stock
Content-Type: application/json
{
  "quantity": 50
}

### Reduce Stock
PUT http://localhost:8082/api/inventory/prod-001/reduce-stock
Content-Type: application/json
{
  "quantity": 5
}

### Get Low Stock Items
GET http://localhost:8082/api/inventory/low-stock

### Delete Inventory
DELETE http://localhost:8082/api/inventory/prod-001


# =====================================================
# 3. CART SERVICE (port 8083)
# =====================================================

### Health Check
GET http://localhost:8083/api/cart/health

### Add Item to Cart
POST http://localhost:8083/api/cart/user123/items
Content-Type: application/json
{
  "productId": "prod-001",
  "productName": "Samsung Galaxy S24",
  "price": 79999.00,
  "quantity": 1
}

### Add Another Item
POST http://localhost:8083/api/cart/user123/items
Content-Type: application/json
{
  "productId": "prod-002",
  "productName": "AirPods Pro",
  "price": 24999.00,
  "quantity": 2
}

### Get Cart
GET http://localhost:8083/api/cart/user123

### Get Cart Summary
GET http://localhost:8083/api/cart/user123/summary

### Update Cart Item
PUT http://localhost:8083/api/cart/user123/items/prod-001
Content-Type: application/json
{
  "productId": "prod-001",
  "productName": "Samsung Galaxy S24",
  "price": 79999.00,
  "quantity": 3
}

### Remove Item from Cart
DELETE http://localhost:8083/api/cart/user123/items/prod-001

### Clear Cart
DELETE http://localhost:8083/api/cart/user123/clear


# =====================================================
# 4. ORDER SERVICE (port 8084)
# =====================================================

### Health Check
GET http://localhost:8084/api/orders/health

### Place Order
POST http://localhost:8084/api/orders/place
Content-Type: application/json
{
  "userId": "user123",
  "items": [
    {
      "productId": "prod-001",
      "productName": "Samsung Galaxy S24",
      "price": 79999.00,
      "quantity": 1
    },
    {
      "productId": "prod-002",
      "productName": "AirPods Pro",
      "price": 24999.00,
      "quantity": 2
    }
  ]
}

### Get All Orders
GET http://localhost:8084/api/orders

### Get Order by Order ID
GET http://localhost:8084/api/orders/ORD-XXXXXXXX
# Replace with actual orderId from Place Order response

### Get Orders by User
GET http://localhost:8084/api/orders/user/user123

### Update Order Status
PUT http://localhost:8084/api/orders/ORD-XXXXXXXX/status
Content-Type: application/json
{
  "status": "CONFIRMED"
}
# Valid statuses: PLACED, CONFIRMED, PACKED, SHIPPED, DELIVERED, CANCELLED

### Delete Order
DELETE http://localhost:8084/api/orders/ORD-XXXXXXXX


# =====================================================
# 5. PAYMENT SERVICE (port 8085)
# =====================================================

### Health Check
GET http://localhost:8085/api/payments/health

### Create Payment (status stays PENDING)
POST http://localhost:8085/api/payments
Content-Type: application/json
{
  "orderId": "ORD-XXXXXXXX",
  "userId": "user123",
  "amount": 129997.00,
  "paymentMode": "UPI"
}

### Simulate Payment (auto sets SUCCESS/FAILED based on mode)
POST http://localhost:8085/api/payments/simulate
Content-Type: application/json
{
  "orderId": "ORD-YYYYYYYY",
  "userId": "user123",
  "amount": 79999.00,
  "paymentMode": "COD"
}
# paymentMode options: UPI, CARD, NET_BANKING, COD
# COD always SUCCESS. Others succeed 80% of the time.

### Get All Payments
GET http://localhost:8085/api/payments

### Get Payment by Payment ID
GET http://localhost:8085/api/payments/PAY-XXXXXXXX

### Get Payments by Order ID
GET http://localhost:8085/api/payments/order/ORD-XXXXXXXX

### Update Payment Status
PUT http://localhost:8085/api/payments/PAY-XXXXXXXX/status
Content-Type: application/json
{
  "status": "REFUNDED"
}
# Valid statuses: PENDING, SUCCESS, FAILED, REFUNDED

### Delete Payment
DELETE http://localhost:8085/api/payments/PAY-XXXXXXXX


# =====================================================
# 6. SEARCH SERVICE (port 8086)
# =====================================================

### Health Check
GET http://localhost:8086/api/search/health

### Index a Product
POST http://localhost:8086/api/search/index
Content-Type: application/json
{
  "productId": "prod-001",
  "name": "Samsung Galaxy S24",
  "description": "Latest Samsung flagship smartphone with AI camera",
  "category": "Electronics",
  "price": 79999.00,
  "active": true
}

### Index More Products (for testing search)
POST http://localhost:8086/api/search/index
Content-Type: application/json
{
  "productId": "prod-002",
  "name": "Apple AirPods Pro",
  "description": "Premium wireless earbuds with noise cancellation",
  "category": "Electronics",
  "price": 24999.00,
  "active": true
}

POST http://localhost:8086/api/search/index
Content-Type: application/json
{
  "productId": "prod-003",
  "name": "Nike Running Shoes",
  "description": "Lightweight running shoes for athletes",
  "category": "Footwear",
  "price": 8999.00,
  "active": true
}

### Search by Keyword
GET http://localhost:8086/api/search?keyword=samsung
GET http://localhost:8086/api/search?keyword=wireless

### Search by Category
GET http://localhost:8086/api/search/category/Electronics

### Search by Price Range
GET http://localhost:8086/api/search/price-range?min=5000&max=30000

### Filter by Category + Price Range
GET http://localhost:8086/api/search/filter?category=Electronics&min=20000&max=90000

### Get Suggestions (autocomplete)
GET http://localhost:8086/api/search/suggestions?keyword=sam

### Delete from Index
DELETE http://localhost:8086/api/search/index/prod-001


# =====================================================
# EXPECTED JSON RESPONSE FORMAT (all services)
# =====================================================
# Success:
{
  "success": true,
  "message": "Product created successfully",
  "data": { ... }
}

# Error:
{
  "success": false,
  "message": "Product not found with id: abc123",
  "data": null
}

# Validation Error:
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Product name is required",
    "price": "Price must be non-negative"
  }
}
