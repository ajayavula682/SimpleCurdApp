# SimpleCurdApp

A comprehensive Spring Boot REST API application demonstrating CRUD (Create, Read, Update, Delete) operations for managing Users and Products with advanced features including Redis caching, Spring Security, and Prometheus monitoring.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Docker Support](#docker-support)
- [Monitoring](#monitoring)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## ✨ Features

### User Management
- Create, read, update, and delete users
- Search users by keyword
- Get users by email
- Activate/deactivate user accounts
- Filter active users
- Duplicate email validation

### Product Management
- Complete CRUD operations for products
- Search products by keyword
- Filter products by category
- Get products by price range
- Filter available products
- Filter in-stock products
- Update product availability
- Update product quantity
- Automatic timestamp tracking (created_at, updated_at)

### Additional Features
- **Redis Caching**: Improved performance with Redis integration
- **Spring Security**: Secured endpoints with authentication
- **Exception Handling**: Global exception handling with custom error responses
- **Validation**: Input validation with Jakarta Bean Validation
- **Actuator**: Health checks and metrics monitoring
- **Prometheus**: Metrics export for monitoring
- **Lombok**: Reduced boilerplate code
- **JPA/Hibernate**: Database abstraction with MySQL support

## 🛠 Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.1
- **Spring Data JPA**: Database operations
- **Spring Security**: Authentication and authorization
- **Spring Boot Actuator**: Application monitoring
- **MySQL**: Primary database
- **Redis**: Caching layer
- **Lombok**: Code generation
- **Maven**: Build tool
- **Micrometer**: Metrics with Prometheus support
- **Docker**: Containerization support

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
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Redis Configuration

```properties
# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### Server Configuration

```properties
# Server Port
server.port=8088
```

### Actuator Endpoints

```properties
# Actuator Configuration
management.endpoints.web.exposure.include=health,metrics,loggers,prometheus
management.endpoint.health.show-details=always
```

## 🏃 Running the Application

### Option 1: Using Maven

```bash
./mvnw spring-boot:run
```

Or:
```bash
mvn spring-boot:run
```

### Option 2: Using JAR file

```bash
./mvnw clean package -DskipTests
java -jar target/SimpleCurdApp-0.0.1-SNAPSHOT.jar
```

### Option 3: Using Docker

```bash
# Build the application
./mvnw clean package -DskipTests

# Build Docker image
docker build -t simplecurdapp:latest .

# Run the container
docker run -p 8088:8088 simplecurdapp:latest
```

The application will start on **http://localhost:8088**

## 📚 API Documentation

### Base URL
```
http://localhost:8088
```

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
curl -X POST http://localhost:8088/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "123-456-7890",
    "address": "123 Main St, City, State"
  }'
```

#### Create Product
```bash
curl -X POST http://localhost:8088/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "description": "High-performance gaming laptop with RTX graphics",
    "price": 1299.99,
    "quantity": 10,
    "category": "Electronics"
  }'
```

#### Search Products
```bash
curl -X GET "http://localhost:8088/api/products/search?keyword=laptop"
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
docker run -p 8088:8088 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/BankDb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  simplecurdapp:latest
```

## 📊 Monitoring

### Actuator Endpoints

The application exposes several actuator endpoints for monitoring:

- **Health Check**: `http://localhost:8088/actuator/health`
- **Metrics**: `http://localhost:8088/actuator/metrics`
- **Prometheus**: `http://localhost:8088/actuator/prometheus`
- **Loggers**: `http://localhost:8088/actuator/loggers`

### Health Check Example
```bash
curl http://localhost:8088/actuator/health
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
