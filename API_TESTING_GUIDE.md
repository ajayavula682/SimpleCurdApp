# CRUD API Testing Guide for SimpleCurdApp

## Starting the Application

First, ensure your application is running on `http://localhost:8082`

### Option 1: Using IDE
- Open the project in your IDE
- Run `SimpleCurdAppApplication.java` main method

### Option 2: Using Maven (if compilation works)
```bash
mvn spring-boot:run
```

### Option 3: Using Java directly
```bash
mvn clean package -DskipTests
java -jar target/SimpleCurdApp-0.0.1-SNAPSHOT.jar
```

## API Endpoints Overview

### Base URLs:
- **Users API**: `http://localhost:8082/api/users`
- **Products API**: `http://localhost:8082/api/products`
- **Info Endpoint**: `http://localhost:8082/api/info`
- **Health Check**: `http://localhost:8082/api/health`
- **H2 Database Console**: `http://localhost:8082/h2-console`

---

## 1. Testing with Browser (GET requests only)

### Basic Health Check:
```
http://localhost:8082/api/health
```

### API Information:
```
http://localhost:8082/api/info
```

### Get All Users:
```
http://localhost:8082/api/users
```

### Get All Products:
```
http://localhost:8082/api/products
```

---

## 2. Testing with cURL Commands

### A. User CRUD Operations

#### Create a New User:
```bash
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "123-456-7890",
    "address": "123 Main St, City, State"
  }'
```

#### Get All Users:
```bash
curl -X GET http://localhost:8082/api/users
```

#### Get User by ID:
```bash
curl -X GET http://localhost:8082/api/users/1
```

#### Update User:
```bash
curl -X PUT http://localhost:8082/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@example.com",
    "phone": "123-456-7890",
    "address": "456 Oak St, City, State",
    "isActive": true
  }'
```

#### Delete User:
```bash
curl -X DELETE http://localhost:8082/api/users/1
```

#### Search Users:
```bash
curl -X GET "http://localhost:8082/api/users/search?keyword=john"
```

#### Get Active Users Only:
```bash
curl -X GET http://localhost:8082/api/users/active
```

#### Deactivate/Activate User:
```bash
curl -X PATCH http://localhost:8082/api/users/1/deactivate
curl -X PATCH http://localhost:8082/api/users/1/activate
```

### B. Product CRUD Operations

#### Create a New Product:
```bash
curl -X POST http://localhost:8082/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "High-performance gaming laptop with RTX graphics",
    "price": 1299.99,
    "quantity": 10,
    "category": "Electronics"
  }'
```

#### Get All Products:
```bash
curl -X GET http://localhost:8082/api/products
```

#### Get Product by ID:
```bash
curl -X GET http://localhost:8082/api/products/1
```

#### Update Product:
```bash
curl -X PUT http://localhost:8082/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Gaming Laptop",
    "description": "Updated high-performance gaming laptop",
    "price": 1199.99,
    "quantity": 15,
    "category": "Electronics",
    "isAvailable": true
  }'
```

#### Delete Product:
```bash
curl -X DELETE http://localhost:8082/api/products/1
```

#### Search Products:
```bash
curl -X GET "http://localhost:8082/api/products/search?keyword=laptop"
```

#### Get Products by Category:
```bash
curl -X GET http://localhost:8082/api/products/category/Electronics
```

#### Get Available Products:
```bash
curl -X GET http://localhost:8082/api/products/available
```

#### Get In-Stock Products:
```bash
curl -X GET http://localhost:8082/api/products/in-stock
```

#### Get Products by Price Range:
```bash
curl -X GET "http://localhost:8082/api/products/price-range?minPrice=100&maxPrice=1500"
```

#### Update Product Availability:
```bash
curl -X PATCH "http://localhost:8082/api/products/1/availability?isAvailable=false"
```

#### Update Product Quantity:
```bash
curl -X PATCH "http://localhost:8082/api/products/1/quantity?quantity=25"
```

---

## 3. Testing with Postman

### Setup:
1. Download and install Postman
2. Create a new collection called "SimpleCurdApp API"
3. Set base URL as environment variable: `{{baseUrl}} = http://localhost:8082`

### Sample Postman Requests:

#### Create User Request:
- **Method**: POST
- **URL**: `{{baseUrl}}/api/users`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "phone": "987-654-3210",
  "address": "789 Pine St, City, State"
}
```

#### Create Product Request:
- **Method**: POST
- **URL**: `{{baseUrl}}/api/products`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse with RGB lighting",
  "price": 29.99,
  "quantity": 50,
  "category": "Accessories"
}
```

---

## 4. Testing with HTTP Client (VS Code REST Client Extension)

Create a file called `api-tests.http`:

```http
### Health Check
GET http://localhost:8082/api/health

### API Info
GET http://localhost:8082/api/info

### Create User
POST http://localhost:8082/api/users
Content-Type: application/json

{
  "name": "Test User",
  "email": "test@example.com",
  "phone": "555-0123",
  "address": "Test Address"
}

### Get All Users
GET http://localhost:8082/api/users

### Get User by ID
GET http://localhost:8082/api/users/1

### Create Product
POST http://localhost:8082/api/products
Content-Type: application/json

{
  "name": "Test Product",
  "description": "This is a test product",
  "price": 99.99,
  "quantity": 5,
  "category": "Test"
}

### Get All Products
GET http://localhost:8082/api/products

### Search Products
GET http://localhost:8082/api/products/search?keyword=test
```

---

## 5. Database Verification

### H2 Console Access:
1. Navigate to: `http://localhost:8082/h2-console`
2. Use these connection settings:
   - **JDBC URL**: `jdbc:h2:mem:testdb`
   - **User Name**: `sa`
   - **Password**: `password`

### Sample SQL Queries:
```sql
-- View all users
SELECT * FROM users;

-- View all products
SELECT * FROM products;

-- View products with quantity > 0
SELECT * FROM products WHERE quantity > 0;

-- View active users
SELECT * FROM users WHERE is_active = true;
```

---

## 6. Expected Response Formats

### User Response:
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "123-456-7890",
  "address": "123 Main St, City, State",
  "isActive": true
}
```

### Product Response:
```json
{
  "id": 1,
  "name": "Gaming Laptop",
  "description": "High-performance gaming laptop",
  "price": 1299.99,
  "quantity": 10,
  "category": "Electronics",
  "isAvailable": true,
  "createdAt": "2026-01-06T19:30:00",
  "updatedAt": "2026-01-06T19:30:00"
}
```

### Error Response:
```json
{
  "message": "User not found with id : '999'",
  "details": "Resource not found",
  "timestamp": "2026-01-06T19:30:00",
  "status": 404,
  "path": "uri=/api/users/999"
}
```

---

## 7. Testing Scenarios

### Complete User Workflow:
1. Create a new user
2. Verify user was created (GET all users)
3. Get specific user by ID
4. Update user information
5. Search for user
6. Deactivate user
7. Verify user is inactive
8. Reactivate user
9. Delete user

### Complete Product Workflow:
1. Create a new product
2. Verify product was created
3. Get specific product by ID
4. Update product details
5. Search for product
6. Filter by category
7. Update quantity
8. Mark as unavailable
9. Delete product

### Error Testing:
1. Try to get non-existent user/product
2. Try to create user with duplicate email
3. Try to update non-existent resource
4. Try to delete non-existent resource

---

## 8. Troubleshooting

### Common Issues:

1. **Connection Refused**: Ensure application is running on port 8082
2. **404 Not Found**: Check URL paths and HTTP methods
3. **400 Bad Request**: Verify JSON format and required fields
4. **500 Internal Server Error**: Check application logs

### Checking Application Status:
```bash
# Check if port 8082 is being used
lsof -i :8082

# Check application logs
tail -f logs/application.log
```

This guide covers all the major ways to test your CRUD API endpoints. Start with simple GET requests to verify the application is running, then move on to more complex CRUD operations.
