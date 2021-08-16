FROM amazoncorretto:11

# Install on /opt/auth-service
WORKDIR /opt/auth-service

# Copy mvnw
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install dependencies
RUN ./mvnw dependency:go-offline

# Copy sources
COPY src ./src

# Expose port 8080
EXPOSE 8080

# Run application
CMD ["./mvnw", "spring-boot:run"]