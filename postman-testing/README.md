# Postman Testing Guide

## Overview
This folder contains Postman collections and environments for testing all SimpleCrudApp APIs.

## Files Included

1. **SimpleCurdApp-API-Collection.json** - Complete API collection with all endpoints
2. **SimpleCurdApp-Environment.json** - Environment variables (base URL, IDs, etc.)

## How to Import in Postman

### Step 1: Open Postman
Launch Postman desktop application

### Step 2: Import Collection
- Click **File** → **Import**
- Select `SimpleCurdApp-API-Collection.json`
- Click **Import**

### Step 3: Import Environment
- Click **File** → **Import**
- Select `SimpleCurdApp-Environment.json`
- Click **Import**

### Step 4: Select Environment
- In the top-right corner, find the **Environment** dropdown
- Select **SimpleCrudApp Environment**

## Available API Endpoints

### Entry Point APIs
- `GET /api/health` - Check API health status
- `GET /api/info` - Get API information
- `GET /api/hello/v1` - Welcome endpoint

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/get/{id}` - Get user with caching
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?keyword=...` - Search products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

## Environment Variables

The environment includes the following variables that can be customized:

- **base_url** - Base URL of the API (default: `http://localhost:8080`)
- **user_id** - User ID for testing (default: `1`)
- **product_id** - Product ID for testing (default: `1`)
- **api_version** - API version (default: `v1`)

## Using Variables in Requests

Variables are referenced using `{{variable_name}}` syntax:
- `{{base_url}}/api/users` - Resolves to `http://localhost:8080/api/users`
- `{{user_id}}` - Resolves to `1`

## Sample Request Bodies

### Create User
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "address": "123 Main St, Anytown, USA"
}
```

### Create Product
```json
{
  "name": "Laptop Pro",
  "description": "High-performance laptop for professionals",
  "price": 1299.99,
  "quantity": 50,
  "category": "Electronics",
  "sku": "LP-PRO-001"
}
```

## Testing Workflow

1. **Health Check** - Start with `GET /api/health` to verify API is running
2. **Create Resources** - Create users and products using POST requests
3. **Read Resources** - Get specific resources using GET requests
4. **Update Resources** - Modify resources using PUT requests
5. **Search/Filter** - Use search endpoints to find products
6. **Delete Resources** - Clean up test data using DELETE requests

## Prerequisites

- Postman installed (Download from https://www.postman.com/downloads/)
- SimpleCrudApp running on `http://localhost:8080`
- Database configured and populated with data

## Tips

- Update the `base_url` variable if your API runs on a different address
- Update `user_id` and `product_id` variables based on actual data in your database
- Use the pre-request scripts for authentication if needed in future
- Create test scripts for automated validation of responses

