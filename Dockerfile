FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY backend/pom.xml pom.xml
COPY backend/src src
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/fashionstore-backend.war /usr/local/tomcat/webapps/ROOT.war
COPY frontend/ /usr/local/tomcat/webapps/ROOT/
EXPOSE 8080
