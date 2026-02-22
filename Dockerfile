
# Multi-stage build
# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /build

# Copy Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /build/target/app.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java","-jar","app.jar"]

