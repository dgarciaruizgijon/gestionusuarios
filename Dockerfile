# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml mvnw .mvn/ ./
RUN mvn -B dependency:go-offline
COPY src src
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]




# FROM eclipse-temurin:21-jre
# WORKDIR /app
# COPY target/gestionusuarios-0.0.1-SNAPSHOT.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/app/app.jar"]






# # Stage 1: Build the application
# FROM eclipse-temurin:21-jdk AS builder
# # Set the working directory
# WORKDIR /app
# # Copy the application code
# COPY . .
# # Given permissions to mvnw
# RUN chmod +x mvnw
# # Build the application (requires Maven or Gradle)
# RUN ./mvnw clean package -DskipTests
# # Stage 2: Run the application
# FROM eclipse-temurin:21-jre
# # Set the working directory
# WORKDIR /app
# # Copy the JAR file from the builder stage
# COPY --from=builder /app/target/*.jar app.jar
# # Expose the port the app will run on
# EXPOSE 8080
# # Command to run the application
# ENTRYPOINT ["java", "-jar", "app.jar"]