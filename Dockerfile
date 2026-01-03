FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# 🔑 Fix permission issue
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
