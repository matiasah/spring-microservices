FROM amazoncorretto:11

# Install on /opt/auth-service
WORKDIR /opt/auth-service

# Copy mvnw
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install dependencies
RUN ./mvnw dependency:resolve dependency:go-offline dependency:resolve-plugins

# Copy sources
COPY src ./src

# Copy frontend
COPY auth-frontend /opt/auth-service/auth-frontend

# Expose port 8080
EXPOSE 8080

# Run application
CMD ["./mvnw", "spring-boot:run"]