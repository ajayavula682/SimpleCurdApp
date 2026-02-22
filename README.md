# SimpleCurdApp

A comprehensive Full-Stack CRUD application with Spring Boot REST API backend and interactive frontend dashboard for managing Users and Products. Features include Redis caching with cache invalidation, Swagger API documentation, Spring Security, real-time UI updates, and Prometheus monitoring.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Frontend Dashboard](#frontend-dashboard)
- [API Documentation](#api-documentation)
- [Swagger UI](#swagger-ui)
- [Docker Support](#docker-support)
- [Monitoring](#monitoring)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## ✨ Features

### Frontend Dashboard
- **Modern UI**: Responsive web interface with tabbed navigation
- **User Management**: Interactive table with search, filter, and CRUD operations
- **Product Management**: Product inventory with filters by category and availability
- **Real-time Updates**: Live data synchronization with backend
- **Modal Forms**: User-friendly forms for creating and editing records
- **Toast Notifications**: Visual feedback for all operations
- **API Info Tab**: Built-in API documentation viewer

### User Management (Backend + Frontend)
- Create, read, update, and delete users
- Search users by keyword (name, email, phone, address)
- Get users by email
- Activate/deactivate user accounts
- Filter active users
- Duplicate email validation
- **Redis caching with automatic cache invalidation**

### Product Management (Backend + Frontend)
- Complete CRUD operations for products
- Search products by keyword (name, description, category)
- Filter products by category
- Get products by price range
- Filter available products
- Filter in-stock products
- Update product availability
- Update product quantity
- Automatic timestamp tracking (created_at, updated_at)
- Category-based organization

### Advanced Features
- **Swagger/OpenAPI Documentation**: Interactive API documentation and testing
- **Redis Caching**: Improved performance with intelligent cache invalidation
- **Spring Security**: Secured endpoints with authentication
- **Global Exception Handling**: Custom error responses with proper HTTP status codes
- **Input Validation**: Jakarta Bean Validation for data integrity
- **Spring Boot Actuator**: Health checks and metrics monitoring
- **Prometheus Integration**: Metrics export for monitoring systems
- **Lombok**: Reduced boilerplate code
- **JPA/Hibernate**: Database abstraction with MySQL support
- **Docker Ready**: Containerization support with Docker and Docker Compose

## 🛠 Technology Stack

### Backend
- **Java**: 17
- **Spring Boot**: 3.2.1
- **Spring Data JPA**: Database operations
- **Spring Security**: Authentication and authorization
- **Spring Boot Actuator**: Application monitoring
- **MySQL**: Primary database (version 8.0+)
- **Redis**: Caching layer with cache invalidation
- **Lombok**: Code generation and boilerplate reduction
- **Maven**: Build and dependency management
- **Micrometer**: Metrics with Prometheus support
- **Springdoc OpenAPI**: Swagger documentation (v2.3.0)

### Frontend
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with flexbox and grid
- **JavaScript (ES6+)**: Interactive functionality
- **Fetch API**: RESTful API communication
- **Responsive Design**: Mobile-friendly interface

### DevOps & Tools
- **Docker**: Containerization support
- **Docker Compose**: Multi-container orchestration
- **Maven Wrapper**: Consistent build environment
- **Git**: Version control

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.6+** (or use the included Maven wrapper)
- **MySQL 8.0+** running on localhost:3306
- **Redis Server** (optional, for caching features) running on localhost:6379
- **Docker** (optional, for containerized deployment)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/ajayavula682/SimpleCurdApp.git
   cd SimpleCurdApp
   ```

2. **Set up MySQL Database**
   ```sql
   CREATE DATABASE BankDb;
   ```

3. **Configure application properties**
   
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/BankDb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Install dependencies**
   ```bash
   ./mvnw clean install
   ```
   
   Or if you have Maven installed:
   ```bash
   mvn clean install
   ```

## ⚙️ Configuration

### Database Configuration

The application uses MySQL by default. Configure the following properties in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/BankDb
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Important**: Changed from `create-drop` to `update` to persist data across application restarts.

### Redis Configuration

```properties
# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

**Note**: Redis is used for caching user data with automatic cache invalidation on create, update, and delete operations.

### Swagger/OpenAPI Configuration

```properties
# Swagger/OpenAPI Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```

### Server Configuration

```properties
# Server Port
server.port=8082
```

### Actuator Endpoints

```properties
# Actuator Configuration
management.endpoints.web.exposure.include=health,metrics,loggers,prometheus
management.endpoint.health.show-details=always
```

## 🏃 Running the Application

### Option 1: Using Maven Wrapper (Recommended)

```bash
./mvnw spring-boot:run
```

### Option 2: Using Maven

```bash
mvn spring-boot:run
```

### Option 3: Using JAR file

```bash
./mvnw clean package -DskipTests
java -jar target/app.jar
```

### Option 4: Using Docker

```bash
# Build the application
./mvnw clean package -DskipTests

# Build Docker image
docker build -t simplecurdapp:latest .

# Run the container
docker run -p 8082:8082 simplecurdapp:latest
```

### Option 5: Using Docker Compose

```bash
docker-compose up -d
```

The application will start on **http://localhost:8082**

### Verify the Application is Running

```bash
# Health check
curl http://localhost:8082/actuator/health

# Expected response: {"status":"UP"}
```

## 🎨 Frontend Dashboard

The application includes a modern, responsive web dashboard for managing users and products.

### Accessing the Dashboard

Open your browser and navigate to:
```
http://localhost:8082
```
Or open the file directly:
```
frontend/index.html
```

### Dashboard Features

#### 1. **Products Tab**
- View all products in a table format
- Search products by name, description, or category
- Filter by category (dropdown)
- Filter by availability status
- Add new products with the "+" button
- Edit products by clicking the Edit button
- Delete products with confirmation
- See real-time stock levels and prices

#### 2. **Users Tab**
- View all users in a table format
- Search users by name, email, phone, or address
- Filter active/inactive users
- Add new users with the "+" button
- Edit user information
- Delete users with confirmation
- Toggle user active status

#### 3. **API Info Tab**
- Quick reference for all API endpoints
- Product API endpoints with descriptions
- User API endpoints with descriptions
- Links to Swagger documentation

### Frontend Features

✅ **Responsive Design** - Works on desktop, tablet, and mobile  
✅ **Real-time Updates** - Changes reflect immediately  
✅ **Search & Filter** - Quick data finding capabilities  
✅ **Modal Forms** - Clean user experience for data entry  
✅ **Toast Notifications** - Visual feedback for all actions  
✅ **Error Handling** - User-friendly error messages  
✅ **Loading States** - Activity indicators during API calls

## 📚 API Documentation

### Base URL
```
http://localhost:8082
```

## 📖 Swagger UI

The application includes comprehensive Swagger/OpenAPI documentation for interactive API testing.

### Accessing Swagger UI

Once the application is running, access Swagger UI at:

**Swagger UI (Interactive)**:
```
http://localhost:8082/swagger-ui.html
```

**OpenAPI JSON Specification**:
```
http://localhost:8082/api-docs
```

**OpenAPI YAML Specification**:
```
http://localhost:8082/api-docs.yaml
```

### Swagger Features

✅ **Interactive Testing** - Test APIs directly from the browser  
✅ **Request/Response Examples** - See data structures clearly  
✅ **Auto-generated Documentation** - Always up-to-date with code  
✅ **Schema Definitions** - Detailed model information  
✅ **Try It Out** - Execute API calls with custom parameters  
✅ **Response Codes** - All possible HTTP status codes documented  

For detailed Swagger documentation, see [SWAGGER_DOCUMENTATION.md](SWAGGER_DOCUMENTATION.md).

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/get/{id}` | Get user data by ID (alternative) |
| GET | `/api/users/search?keyword={keyword}` | Search users |
| GET | `/api/users/active` | Get all active users |
| GET | `/api/users/email/{email}` | Get user by email |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |
| PATCH | `/api/users/{id}/activate` | Activate user |
| PATCH | `/api/users/{id}/deactivate` | Deactivate user |

### Product Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?keyword={keyword}` | Search products |
| GET | `/api/products/category/{category}` | Get products by category |
| GET | `/api/products/available` | Get available products |
| GET | `/api/products/in-stock` | Get in-stock products |
| GET | `/api/products/price-range?minPrice={min}&maxPrice={max}` | Get products by price range |
| GET | `/api/products/categories` | Get all categories |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| PATCH | `/api/products/{id}/availability?isAvailable={boolean}` | Update availability |
| PATCH | `/api/products/{id}/quantity?quantity={number}` | Update quantity |

### Example Requests

#### Create User
```bash
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "555-0101",
    "address": "123 Main St, New York, NY 10001",
    "isActive": true
  }'
```

#### Create Product
```bash
curl -X POST http://localhost:8082/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "High-performance gaming laptop with RTX graphics",
    "price": 1299.99,
    "quantity": 10,
    "category": "Electronics",
    "isAvailable": true
  }'
```

#### Search Products
```bash
curl -X GET "http://localhost:8082/api/products/search?keyword=laptop"
```

#### Get Available Products
```bash
curl -X GET "http://localhost:8082/api/products/available"
```

#### Update User
```bash
curl -X PUT http://localhost:8082/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com",
    "phone": "555-0102",
    "address": "456 Oak Ave, Los Angeles, CA 90001",
    "isActive": true
  }'
```

#### Get Products by Price Range
```bash
curl -X GET "http://localhost:8082/api/products/price-range?minPrice=100&maxPrice=500"
```

For more detailed API testing examples, see [API_TESTING_GUIDE.md](API_TESTING_GUIDE.md).

## 🐳 Docker Support

The application includes a Dockerfile for containerization.

### Build Docker Image
```bash
./mvnw clean package -DskipTests
docker build -t simplecurdapp:latest .
```

### Run Container
```bash
docker run -p 8082:8082 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/BankDb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  -e SPRING_DATA_REDIS_HOST=host.docker.internal \
  simplecurdapp:latest
```

### Using Docker Compose

The application includes a `docker-compose.yml` file for easy multi-container setup:

```bash
# Start all services (app, mysql, redis)
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

## 📊 Monitoring

### Actuator Endpoints

The application exposes several actuator endpoints for monitoring:

- **Health Check**: `http://localhost:8082/actuator/health`
- **Metrics**: `http://localhost:8082/actuator/metrics`
- **Prometheus**: `http://localhost:8082/actuator/prometheus`
- **Loggers**: `http://localhost:8082/actuator/loggers`

### Health Check Example
```bash
curl http://localhost:8082/actuator/health
```

Response:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "redis": {
      "status": "UP"
    }
  }
}
```

## 📁 Project Structure

```
SimpleCurdApp/
├── src/
│   ├── main/
│   │   ├── java/com/example/simplecurdapp/
│   │   │   ├── config/             # Configuration classes
│   │   │   │   ├── RedisConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/         # REST controllers
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── EntryPointOne.java
│   │   │   ├── model/              # Entity models
│   │   │   │   ├── User.java
│   │   │   │   └── Product.java
│   │   │   ├── repository/         # JPA repositories
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── ProductRepository.java
│   │   │   ├── service/            # Business logic
│   │   │   │   ├── UserService.java
│   │   │   │   └── ProductService.java
│   │   │   ├── exception/          # Exception handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   └── ErrorResponse.java
│   │   │   └── SimpleCurdAppApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/simplecurdapp/
│           └── SimpleCurdAppApplicationTests.java
├── target/                         # Build output
├── Dockerfile                      # Docker configuration
├── pom.xml                         # Maven configuration
├── API_TESTING_GUIDE.md           # Detailed API testing guide
└── README.md                       # This file
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is available for educational and demonstration purposes.

## 👤 Author

**Ajay Avula**
- GitHub: [@ajayavula682](https://github.com/ajayavula682)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- All contributors and users of this project

---

**Note**: This is a demonstration project showcasing Spring Boot CRUD operations with modern features. For production use, ensure proper security configurations, environment-specific settings, and comprehensive testing.
