FROM maven:3.8.5-openjdk-18-slim
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18.0-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]