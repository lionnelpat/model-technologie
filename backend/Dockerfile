# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

# Copier les fichiers pom
COPY pom.xml .

# Copier le code source
COPY src ./src

# Build avec Maven
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests && \
    ls -la target/

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Labels
LABEL maintainer="Model Technologie <business.modeltech@gmail.com>"
LABEL description="Model Technologie Backend API - Spring Boot Application"
LABEL version="0.1.0"

# Créer un user non-root pour sécurité
RUN addgroup -g 1000 appuser && \
    adduser -D -u 1000 -G appuser appuser

WORKDIR /app

# Copier le JAR depuis le stage de build
COPY --from=builder /build/target/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget -q -O- http://localhost:8080/api/actuator/health || exit 1

# Permissions
RUN chown -R appuser:appuser /app

# User non-root
USER appuser

# Expose port
EXPOSE 8080

# Environment variables
ENV SPRING_PROFILES_ACTIVE=staging \
    JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# Run
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
