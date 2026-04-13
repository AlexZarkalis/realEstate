# Build Spring Boot app
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Create final image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/target/real-estate-0.0.1-SNAPSHOT.jar ./application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]