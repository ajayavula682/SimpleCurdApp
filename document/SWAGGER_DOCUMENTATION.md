# Swagger API Documentation

## Overview
Swagger/OpenAPI documentation has been successfully integrated into SimpleCurdApp. This provides interactive API documentation where you can explore and test all available endpoints.

## Accessing Swagger UI

Once your application is running, you can access the Swagger UI at:

### Swagger UI (Interactive Documentation)
```
http://localhost:8082/swagger-ui.html
```
or
```
http://localhost:8082/swagger-ui/index.html
```

### OpenAPI JSON Specification
```
http://localhost:8082/api-docs
```

### OpenAPI YAML Specification
```
http://localhost:8082/api-docs.yaml
```

## Features

### 📦 API Groups
The documentation is organized into two main groups:
1. **User Management** - All user-related endpoints
2. **Product Management** - All product-related endpoints

### 🔍 Available Endpoints

#### User Management APIs
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/get/{id}` - Get user data with caching
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users/active` - Get all active users
- `GET /api/users/search` - Search users by keyword
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update user information
- `DELETE /api/users/{id}` - Delete a user
- `PATCH /api/users/{id}/activate` - Activate a user
- `PATCH /api/users/{id}/deactivate` - Deactivate a user

#### Product Management APIs
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search` - Search products by keyword
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/available` - Get available products
- `GET /api/products/in-stock` - Get in-stock products
- `GET /api/products/price-range` - Get products by price range
- `GET /api/products/categories` - Get all categories
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update product information
- `DELETE /api/products/{id}` - Delete a product
- `PATCH /api/products/{id}/availability` - Update product availability
- `PATCH /api/products/{id}/quantity` - Update product quantity

## How to Use Swagger UI

1. **Start your application**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Open your browser** and navigate to:
   ```
   http://localhost:8082/swagger-ui.html
   ```

3. **Explore APIs**
   - Click on any endpoint group (User Management or Product Management)
   - Click on an individual endpoint to see details
   - View request/response schemas, parameters, and examples

4. **Test APIs**
   - Click "Try it out" button on any endpoint
   - Fill in the required parameters
   - Click "Execute" to send the request
   - View the response body, headers, and status code

## Configuration

The Swagger configuration is located in:
- **Config Class**: `src/main/java/com/example/simplecurdapp/config/SwaggerConfig.java`
- **Application Properties**: `src/main/resources/application.properties`

### Swagger Properties
```properties
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

## API Documentation Details

Each endpoint includes:
- ✅ Summary and description
- ✅ Request parameters with descriptions
- ✅ Request body schema
- ✅ Response codes (200, 201, 204, 400, 404, 409)
- ✅ Response body schema
- ✅ Error responses

## Example: Testing an API

### Creating a New User
1. Navigate to **User Management** section
2. Find `POST /api/users` endpoint
3. Click "Try it out"
4. Enter request body:
   ```json
   {
     "name": "John Doe",
     "email": "john.doe@example.com",
     "phone": "555-0101",
     "address": "123 Main St",
     "isActive": true
   }
   ```
5. Click "Execute"
6. View the response (should return 201 Created)

## Benefits

✅ **Interactive Testing** - Test APIs directly from the browser  
✅ **Auto-generated Documentation** - Always up-to-date with code  
✅ **Request/Response Examples** - See data structures clearly  
✅ **No Additional Tools Needed** - No need for Postman or cURL  
✅ **Shareable** - Share API documentation URL with team members  

## Production Considerations

For production environments, you may want to:
- Disable Swagger UI: `springdoc.swagger-ui.enabled=false`
- Secure the documentation endpoints
- Use API keys or authentication
- Configure CORS properly

## Troubleshooting

### Swagger UI not loading?
- Ensure application is running on port 8082
- Check console for any errors
- Verify dependencies in `pom.xml`

### Endpoints not showing?
- Rebuild the project: `./mvnw clean compile`
- Restart the application
- Check controller annotations

## Dependencies Used

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

---

**Happy API Testing! 🚀**
