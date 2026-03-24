
# Multi-stage build
# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /build

# Copy Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies in a cache-friendly layer
RUN ./mvnw -B -DskipTests dependency:go-offline

# Copy source code
COPY src ./src

# Build the application jar
RUN ./mvnw -B clean package -DskipTests

# Stage 2: Run the application
FROM gcr.io/distroless/java17-debian12:nonroot

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /build/target/app.jar /app/app.jar

EXPOSE 8088

ENTRYPOINT ["java","-jar","app.jar"]

