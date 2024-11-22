FROM openjdk:17-jdk-alpine
LABEL authors="Miguel Silva"
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/sales.analysis.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app/sales.analysis.jar"]