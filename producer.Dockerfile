FROM maven:3-jdk-11-slim AS rabbit-tutorials-build

WORKDIR /src
ADD ./pom.xml .
RUN RUN mvn package dependency:go-offline

COPY . .
RUN mvn package --offline

FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=rabbit-tutorials-build /src/target/producer-jar-with-dependencies.jar /app

CMD ["java", "-jar", "producer-jar-with-dependencies.jar"]
