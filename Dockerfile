FROM maven:3.9.11-amazoncorretto-17-al2023 AS maven-container

WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM eclipse-temurin:17.0.17_10-jre-ubi9-minimal

RUN adduser -Dh /home/bfwg bfwg
WORKDIR /app
COPY --from=maven-container /usr/src/app/target/demo-0.1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/app/demo-0.1.0-SNAPSHOT.jar"]

