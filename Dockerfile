FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY backend/pom.xml pom.xml
COPY backend/src src
COPY frontend/ src/main/webapp/

RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk17

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       ca-certificates curl iptables \
    && rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://tailscale.com/install.sh | sh

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/fashionstore-backend.war \
    /usr/local/tomcat/webapps/ROOT.war

COPY start-tailscale.sh /usr/local/bin/start-tailscale.sh

RUN chmod +x /usr/local/bin/start-tailscale.sh

EXPOSE 8080

CMD ["/bin/sh", "-c", "/usr/local/bin/start-tailscale.sh & exec catalina.sh run"]
