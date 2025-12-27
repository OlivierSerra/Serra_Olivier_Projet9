FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml

COPY demo/pom.xml ./demo/pom.xml
COPY gateway/pom.xml ./gateway/pom.xml
COPY notes/pom.xml ./notes/pom.xml
COPY riskService/pom.xml ./riskService/pom.xml
COPY frontService/pom.xml ./frontService/pom.xml
COPY coverage/pom.xml ./coverage/pom.xml


COPY demo/src ./demo/src

RUN mvn -pl demo -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/demo/target/*.jar app.jar

EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
