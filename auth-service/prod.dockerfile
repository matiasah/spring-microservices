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

# Compile project
RUN ./mvnw clean install -DskipTests

# Expose port 8080
EXPOSE 8080

# Run application
CMD ["java", "-jar", "./target/auth-service-0.0.1.jar"]